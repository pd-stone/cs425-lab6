package edu.jsu.mcis.lab6.dao;

import edu.jsu.mcis.lab6.dao.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrainingSessionsDAO {
    private final DAOFactory daoFactory;

    private final String QUERY_SELECT_ALL = "SELECT * FROM session";
    private final String QUERY_SELECT_BY_ID = "SELECT * FROM (registration JOIN attendee ON attendee.id=registration.attendeeid) WHERE registration.sessionid = ?";

    TrainingSessionsDAO(DAOFactory dao) {
        this.daoFactory = dao;
    }
    
    public String list() {

        JSONArray json = new JSONArray();

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(QUERY_SELECT_ALL);
            boolean hasresults = ps.execute();
            if (hasresults) {

                rs = ps.getResultSet();

                JSONObject json_object = new JSONObject();

                json_object.put("success", hasresults);
                json.add(json_object);
                while (rs.next()) {
                    JSONObject temp = new JSONObject();

                    temp.put("id", rs.getInt("id"));
                    temp.put("description", rs.getString("description"));

                    json.add(temp);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return JSONValue.toJSONString(json);

    }
    
    public String find(int id) {

        JSONArray json = new JSONArray();

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
            ps.setInt(1, id);

            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();

                Map success = new LinkedHashMap<>();
                success.put("success", hasresults);
                json.add(success);

                while (rs.next()) {
                    Map json_map = new LinkedHashMap<>();
                    json_map.put("attendeeid", rs.getInt("attendeeid"));
                    json_map.put("sessionid", rs.getInt("sessionid"));
                    json_map.put("firstname", rs.getString("firstname"));
                    json_map.put("lastname", rs.getString("lastname"));
                    json_map.put("displayname", rs.getString("displayname"));
                    json.add(json_map);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return JSONValue.toJSONString(json);
    }

}
