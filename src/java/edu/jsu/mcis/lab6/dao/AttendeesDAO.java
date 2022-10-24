package edu.jsu.mcis.lab6.dao;

import edu.jsu.mcis.lab6.dao.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.*;
import java.sql.Statement;

public class AttendeesDAO {
        private final DAOFactory daoFactory;

        private final String QUERY_REGISTRATION_NUMBER = "SELECT CONCAT(\"R\", LPAD(attendeeid, 6, 0)) AS num FROM registration WHERE attendeeid = ?";
        private final String QUERY_SELECT_BY_ID = "SELECT * FROM attendee a WHERE id = ?";
        private final String QUERY_CREATE = "INSERT INTO attendee (firstname, lastname, displayname) VALUES (?,?,?)";
        private final String QUERY_UPDATE = "UPDATE attendee SET firstname = ?, lastname = ?, displayname = ? WHERE id = 51";

        AttendeesDAO(DAOFactory dao) {
                this.daoFactory = dao;
        }

        /*
         * ------------------------Public Methods---------------------------
         */

        /**
         * retrieve the profile information for an attendee (that is, the attendee's
         * first name, last name, and display name),
         * 
         * @param attendeeid
         * @return
         */
        public String find(int attendeeid) {
                JSONObject json = new JSONObject();
                json.put("success", false);

                Connection conn = daoFactory.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                        ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
                        ps.setInt(1, attendeeid);

                        boolean hasresults = ps.execute();

                        if (hasresults) {

                                rs = ps.getResultSet();

                                if (rs.next()) {

                                        json.put("success", hasresults);

                                        json.put("firstname", rs.getString("firstname"));
                                        json.put("lastname", rs.getString("lastname"));
                                        json.put("displayname", rs.getString("displayname"));

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
                json.put("registrationnumber", getRegistrationNumberByAttendeeID(attendeeid));
                return JSONValue.toJSONString(json);
        }

        /**
         * Updates information on existing attendee
         * 
         * @param attendeeid
         * @return
         */
        /*
         * public String update(int attendeeid) {
         * 
         * }
         */
        /**
         * Adds a new Attendee
         * 
         * @param attendee
         * @return
         */
        public String create(String firstname, String lastname, String displayname) {
                JSONObject json = new JSONObject();

                json.put("success", false);
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                        Connection conn = daoFactory.getConnection();

                        ps = conn.prepareStatement(QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, firstname);
                        ps.setString(2, lastname);
                        ps.setString(3, displayname);
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

        /*
         * ------------------------Private Methods---------------------------
         */

        /**
         * Returns 6 digit registation code based on attendees unique id
         * Attendees who have been registered for a session should be given a six-digit
         * registration code,
         * based on their unique ID, which begins with the letter "R"; for example,
         * if an attendee is registered whose ID has a value of 76, the registration
         * number should be "R000076".
         * 
         * @param attendeeid
         * @return
         */

        private String getRegistrationNumberByAttendeeID(int attendeeid) {

                Connection conn = daoFactory.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                String result = "";
                try {

                        ps = conn.prepareStatement(QUERY_REGISTRATION_NUMBER);
                        ps.setInt(1, attendeeid);

                        boolean hasresults = ps.execute();

                        if (hasresults) {

                                rs = ps.getResultSet();

                                if (rs.next()) {

                                        result = rs.getString("num");

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
                return result;
        }
}