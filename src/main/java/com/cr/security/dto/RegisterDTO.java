package com.cr.security.dto;

import com.cr.security.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
