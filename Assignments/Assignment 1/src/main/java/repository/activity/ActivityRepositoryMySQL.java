package repository.activity;

import model.Activity;
import model.builder.ActivityBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACTIVITY;

public class ActivityRepositoryMySQL implements  ActivityRepository{

    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activities=null;

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + ACTIVITY;
            ResultSet resultSet = statement.executeQuery(sql);

            activities=new ArrayList<>();
            while (resultSet.next()) {
                Activity activity = createActivityFromResultSet(resultSet);
                activities.add(activity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    public Activity findById(Long id) throws EntityNotFoundException {
        Activity activity;

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + ACTIVITY + " where `id`=\'" + id + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()) {
                activity = createActivityFromResultSet(resultSet);
            }
            else
            {
                throw new EntityNotFoundException(id, Activity.class.getSimpleName());
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new EntityNotFoundException(id, Activity.class.getSimpleName());
        }

        return activity;
    }

    @Override
    public List<Activity> findByEmployeeId(Long userId) {
        List<Activity> activities = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + ACTIVITY + " where `user_id`=\'" + userId + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next())
            {
                Activity activity = createActivityFromResultSet(resultSet);
                activities.add(activity);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return activities;
    }

    @Override
    public boolean save(Activity activity) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO "+ACTIVITY+" values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, activity.getType());
            statement.setLong(2, activity.getEmployeeId());
            statement.setDate(3, new Date(activity.getDate().getTime()));
            statement.setObject(4, activity.getClientId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long activityId = resultSet.getLong(1);
            activity.setId(activityId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + ACTIVITY + " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Activity createActivityFromResultSet(ResultSet resultSet) throws SQLException {
        Activity activity = new ActivityBuilder()
                .setId(resultSet.getLong("id"))
                .setDate(new java.util.Date(resultSet.getDate("date").getTime()))
                .setType(resultSet.getString("type"))
                .setPerformer(resultSet.getLong("user_id"))
                .setClientId(resultSet.getLong("client_id"))
                .build();

        return activity;
    }
}
