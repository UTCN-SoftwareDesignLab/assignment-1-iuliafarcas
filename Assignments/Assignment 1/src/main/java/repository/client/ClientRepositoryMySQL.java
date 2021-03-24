package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = null;

        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from " + CLIENT;
            ResultSet resultSet = statement.executeQuery(sql);

            clients = new ArrayList<>();
            while(resultSet.next())
            {
                Client client = createClientFromresultSet(resultSet);

                clients.add(client);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        Client client;

        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from " + CLIENT + " where `id`=\'" + id + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next())
            {
                client = createClientFromresultSet(resultSet);

                return client;
            }
            else
            {
                throw new EntityNotFoundException(id,Client.class.getSimpleName());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new EntityNotFoundException(id,Client.class.getSimpleName());
        }

    }

    @Override
    public Client findByCnp(String cnp) throws EntityNotFoundException {
        Client client;

        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from " + CLIENT + " where `cnp`=\'" + cnp + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next())
            {
                client = createClientFromresultSet(resultSet);

                return client;
            }
            else
            {
                throw new EntityNotFoundException(Long.parseLong(cnp),Client.class.getSimpleName());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new EntityNotFoundException(Long.parseLong(cnp),Client.class.getSimpleName());
        }

    }

    @Override
    public Notification<Client> save(Client client) {
        Notification<Client> notification=new Notification<>();

        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)",  Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3,  client.getCardNb());
            statement.setString(4, client.getCnp());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long clientId = resultSet.getLong(1);
            client.setId(clientId);

            notification.setResult(client);
            return notification;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            notification.addError("Saving account not succesfull");
        }

        return  notification;
    }

    @Override
    public boolean update(Long id, Client client) {

        try {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE "+ CLIENT +" SET name= ?,address= ?, card_nb= ?, cnp= ? WHERE id= ?");
            statement.setString(1, client.getName());
            statement.setString(2,  client.getCardNb());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getCnp());
            statement.setLong(5, id);
            statement.executeUpdate();

            client.setId(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            PreparedStatement removeClientStatement = connection
                    .prepareStatement("DELETE FROM " + CLIENT + " WHERE id= ?");
            removeClientStatement.setLong(1, id);
            removeClientStatement.executeUpdate();
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
            String sql = "DELETE from " + CLIENT;
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Client createClientFromresultSet(ResultSet resultSet) throws SQLException {
        Client  client = new ClientBuilder()
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setAddress(resultSet.getString("address"))
                .setCardNb(resultSet.getString("card_nb"))
                .setCnp(resultSet.getString("cnp"))
                .build();

        return client;
    }
}
