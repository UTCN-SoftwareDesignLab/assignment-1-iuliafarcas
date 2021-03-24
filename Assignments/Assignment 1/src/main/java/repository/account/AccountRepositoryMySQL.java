package repository.account;

import model.Account;
import model.Activity;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import  static  database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts=null;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + ACCOUNT ;
            ResultSet resultSet = statement.executeQuery(sql);

            accounts=new ArrayList<Account>();
            while(resultSet.next()) {
                Account account = createAccountFromResultSet(resultSet);
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        Account account = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + ACCOUNT + " where `id`=\'" + id + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()) {
                account = createAccountFromResultSet(resultSet);
            }
            else
            {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
        return account;
    }

    @Override
    public Notification<Account> save(Account account, Long client_id) {
        Notification<Account> notificationSaveAccount=new Notification<>();

        try {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO "+ACCOUNT+" values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getType());
            statement.setLong(2, client_id);
            statement.setFloat(3, account.getBalance());
            statement.setDate(4, new java.sql.Date(account.getCreation().getTime()));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long accountId = resultSet.getLong(1);
            account.setId(accountId);
            account.setClient_id(client_id);

            notificationSaveAccount.setResult(account);
            return notificationSaveAccount;

        } catch (SQLException e) {
            e.printStackTrace();
            notificationSaveAccount.addError("Saving account not successful");
            return notificationSaveAccount;
        }
    }

    @Override
    public Account findAccountByClientId(Long ownerId) throws EntityNotFoundException {
        Account account;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + ACCOUNT + " where `client_id`=\'" + ownerId + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()) {
                account = createAccountFromResultSet(resultSet);
            }
            else
            {
                throw new EntityNotFoundException(ownerId, Account.class.getSimpleName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(ownerId, Account.class.getSimpleName());
        }
        return account;
    }

    @Override
    public boolean update(Long id, Account account) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE "+ ACCOUNT+" SET type= ?,balance= ?, creation= ? WHERE id= ?");
            updateAccountStatement.setString(1, account.getType());
            updateAccountStatement.setFloat(2, account.getBalance());
            updateAccountStatement.setDate(3,  new java.sql.Date(account.getCreation().getTime()));
            updateAccountStatement.setLong(4, id);
            updateAccountStatement.executeUpdate();

            account.setId(id);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM " + ACCOUNT + " WHERE id= ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ACCOUNT+" where id >= 0";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account createAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new AccountBuilder()
                .setId(resultSet.getLong("id"))
                .setCreation(resultSet.getDate("creation"))
                .setBalance(resultSet.getFloat("balance"))
                .setType(resultSet.getString("type"))
                .setClientId(resultSet.getLong("client_id"))
                .build();

        return account;
    }
}
