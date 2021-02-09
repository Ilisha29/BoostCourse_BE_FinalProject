package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.UserDao;
import kr.or.connect.reservation.dao.UserRoleDao;
import kr.or.connect.reservation.dto.User;
import kr.or.connect.reservation.dto.UserRole;
import kr.or.connect.reservation.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private UserRoleDao userRoleDao;

	public UserServiceImpl(UserDao userDao, UserRoleDao userRoleDao) {
		this.userDao = userDao;
		this.userRoleDao = userRoleDao;
	}

	@Override
	public User getUser(String loginUserId) {
		return userDao.getMemberByEmail(loginUserId);
	}

	@Override
	public List<UserRole> getUserRoles(String loginUserId) {
		return userRoleDao.getRolesByEmail(loginUserId);
	}

	@Override
	public void addUser(User user) {
		userDao.addUser(
				new User(user.getName(),
						user.getPassword(),
						user.getEmail(),
						user.getPhone()
						));
	}

	@Override
	public void addUserRole(String email) {
		int userId = userDao.getMemberByEmail(email).getId();
		userRoleDao.addUserRole(userId);
	}
	
}
