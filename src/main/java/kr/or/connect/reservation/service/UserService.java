package kr.or.connect.reservation.service;

import kr.or.connect.reservation.dto.User;

public interface UserService extends UserDbService {
	void addUser(User user);

	void addUserRole(String email);
}