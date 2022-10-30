package edu.jsu.mcis.lab6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.*;
import java.sql.Statement;

public class RegistrationDAO {

    private final DAOFactory daoFactory;

    private final String QUERY_SELECT_BY_ID = "SELECT * FROM "
            + "((registration JOIN attendee ON registration.attendeeid = attendee.id) "
            + "JOIN `session` ON registration.sessionid = `session`.id) "
            + "WHERE `session`.id = ? AND attendee.id = ?";

    private final String QUERY_CREATE = "INSERT INTO registration (attendeeid, sessionid)"
            + "VALUES (?,?)";

    private final String QUERY_UPDATE = "UPDATE registration SET attendeeid = ?, sessionid = ?"
            + "WHERE (attendeeid = ? AND sessionid = ?)";

    private final String QUERY_DELETE = "DELETE FROM registration WHERE (attendeeid = ? AND sessionid = ?)";

    RegistrationDAO(DAOFactory dao) {
        this.daoFactory = dao;
    }

    public String find(int sessionid, int attendeeid) {

        JSONObject json = new JSONObject();
        json.put("success", false);

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
            ps.setInt(1, sessionid);
            ps.setInt(2, attendeeid);

            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();

                if (rs.next()) {

                    json.put("success", hasresults);

                    json.put("attendeeid", rs.getInt("attendeeid"));
                    json.put("sessionid", rs.getInt("sessionid"));
                    json.put("firstname", rs.getString("firstname"));
                    json.put("lastname", rs.getString("lastname"));
                    json.put("displayname", rs.getString("displayname"));
                    json.put("session", rs.getString("description"));

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {rs.close();rs = null;} catch (Exception e) {e.printStackTrace();}
            }
            if (ps != null) {
                try {ps.close();ps = null;} catch (Exception e) {e.printStackTrace();}
            }
            if (conn != null) {
                try {conn.close();conn = null;} catch (Exception e) {e.printStackTrace();}
            }
        }

        return JSONValue.toJSONString(json);

    }

    public String create(int sessionid, int attendeeid) {
        JSONObject json = new JSONObject();

        json.put("success", false);
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            ps = conn.prepareStatement(QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, attendeeid);
            ps.setInt(2, sessionid);

            int updateCount = ps.executeUpdate();

            if (updateCount > 0) {
                json.put("success", true);
                json.put("rowsAffected", updateCount);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return JSONValue.toJSONString(json);
    }
   
    public String update(int sessionid_old, int attendeeid_old, int sessionid_updated, int attendeeid_updated) {
        JSONObject json = new JSONObject();

        json.put("success", false);
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            ps = conn.prepareStatement(QUERY_UPDATE);
            ps.setInt(1, attendeeid_old);
            ps.setInt(2, sessionid_old);
            ps.setInt(3, attendeeid_updated);
            ps.setInt(4, sessionid_updated);

            int updateCount = ps.executeUpdate();

            if (updateCount > 0) {
                json.put("success", true);
                json.put("rowsAffected", updateCount);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return JSONValue.toJSONString(json);
    }

    public String delete(int attendeeid, int sessionid) {
        JSONObject json = new JSONObject();

        json.put("success", false);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection conn = daoFactory.getConnection();

            ps = conn.prepareStatement(QUERY_DELETE);
            ps.setInt(1, attendeeid);
            ps.setInt(2, sessionid);

            int updateCount = ps.executeUpdate();

            if (updateCount > 0) {
                json.put("success", true);
                json.put("rowsAffected", updateCount);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return JSONValue.toJSONString(json);
    }

}