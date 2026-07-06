package com.example.demo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Tài khoản không được để trống")
    @Size(max = 15, message = "Tài khoản không được quá 15 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 15, message = "Mật khẩu phải từ 8 đến 15 ký tự")
    private String password;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 20, message = "Tên không được quá 20 ký tự")
    private String name;

    @NotNull(message = "Vui lòng chọn chức vụ")
    @Min(value = 0, message = "Role không hợp lệ")
    @Max(value = 1, message = "Role không hợp lệ")
    private Integer role;
}