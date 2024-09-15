package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {

		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("INSERT INTO department " + "(Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());

			int rows = st.executeUpdate();

			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);

					obj.setId(id);
					System.out.println("New department " + obj.getName() + " created on the DB.");
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department obj) {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE department " + "SET Name = ? " + "WHERE Id = ? ");

			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			st.executeUpdate();

			System.out.println("Department updated.");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("DELETE FROM department " + "WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();

			System.out.println("Department deleted");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement st = null;

		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM department " + "WHERE Id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {

				Department dp = new Department();

				dp.setId(rs.getInt("Id"));//ou rs.getInt(1);
				dp.setName(rs.getString("Name"));//ou rs.getString(2);

				return dp;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;

		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");

			rs = st.executeQuery();

			List<Department> DepartmentList = new ArrayList<>();
			
			while (rs.next()) {

				Department dp = new Department();

				dp.setId(rs.getInt(1));
				dp.setName(rs.getString(2));

				DepartmentList.add(dp);
				
			}

			return DepartmentList;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
