package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.User;
import kr.or.connect.reservation.dto.UserRole;

public interface UserDbService {
	public User getUser(String loginUserId);

	public List<UserRole> getUserRoles(String loginUserId);
}