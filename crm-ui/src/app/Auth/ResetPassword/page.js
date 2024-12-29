"use client";
import React, { useState } from "react";
import {
  Button,
  Container,
  FormControl,
  IconButton,
  InputAdornment,
  InputLabel,
  OutlinedInput,
  Paper,
  TextField,
} from "@mui/material";
import { useRouter } from "next/navigation";
import "./ResetPasswordStyle.css";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { toast } from "react-toastify";

const ResetPassword = () => {
  //State dữ liệu mật khẩu
  const [password, setPassword] = useState({
    new: "",
    reNew: "",
  });

  //State ẩn hiện mật khẩu
  const [showPassword, setShowPassword] = useState(false);
  const [showRePassword, setShowRePassword] = useState(false);

  //Function xử lý hiện ẩn mật khẩu
  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const handleMouseUpPassword = (event) => {
    event.preventDefault();
  };

  //Function xử lý hiện ẩn nhập lại mật khẩu
  const handleClickShowRePassword = () => setShowRePassword((show) => !show);

  const handleMouseDownRePassword = (event) => {
    event.preventDefault();
  };

  const handleMouseUpRePassword = (event) => {
    event.preventDefault();
  };

  //State chuyển trang
  const fowardUrl = useRouter();

  //Function thay đổi sự kiện inputChange
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPassword({
      ...password,
      [name]: value,
    });
  };

  //Function validate
  const validateForm = () => {
    //Biểu thức chính quy mật khẩu
    const patternPassword = /^(?=.*[a-zA-Z]).{8,}$/;

    if (
      (password.new === "" || password.new === null) &&
      (password.reNew === "" || password.reNew === null)
    ) {
      toast.warning("Vui lòng nhập đầy đủ thông tin");
      return false;
    } else {
      if (password.new === "") {
        toast.warning("Vui lòng nhập mật khẩu mới");
        return false;
      } else if (
        password.new.length < 8 ||
        !patternPassword.test(password.new)
      ) {
        toast.warning(
          "Mật khẩu phải chứa ít nhất 8 ký tự bao gồm chữ hoa hoặc thường"
        );
        return false;
      }

      if (password.reNew === "") {
        toast.warning("Vui lòng nhập lại mật khẩu mới");
        return false;
      } else if (password.new !== password.reNew) {
        toast.warning("Mật khẩu mới và xác nhận lại mật khẩu không khớp");
        return false;
      }
    }
    return true;
  };

  //Function reset password
  const handleResetPassword = async () => {
    if (validateForm()) {
      const idToast = toast.loading("Vui lòng chờ...");
      setTimeout(() => {
        toast.update(idToast, {
          render: "Đổi mật khẩu thành công",
          type: "success",
          isLoading: false,
          autoClose: 5000,
          closeButton: true,
        });
      }, 500);
      console.log(password);
      fowardUrl.push("/Auth/Form");
    }
  };
  return (
    <>
      {/* chế độ lưới canh giữa chiều ngang và dọc đặt chiều cao của phần tử bao ngoài bằng toàn bộ chiều cao màn hình */}
      <div className="grid place-items-center h-screen">
        <Container maxWidth="sm">
          <Paper className="p-5">
            <div className="text-center mb-5">
              <h2 className="font-bold text-xl mb-2">Đặt lại mật khẩu</h2>
            </div>
            <div className="mb-5">
              <TextField
                id="outlined-basic"
                label="Email"
                variant="outlined"
                type="password"
                // value={password.new}
                disabled
                size="small"
                fullWidth
                name="email"
              />
            </div>
            <FormControl
              className="mb-5"
              variant="outlined"
              fullWidth
              size="small"
            >
              <InputLabel htmlFor="outlined-adornment-password">
                Mật khẩu mới
              </InputLabel>
              <OutlinedInput
                id="outlined-adornment-password"
                type={showPassword ? "text" : "password"}
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label={
                        showPassword
                          ? "hide the password"
                          : "display the password"
                      }
                      onClick={handleClickShowPassword}
                      onMouseDown={handleMouseDownPassword}
                      onMouseUp={handleMouseUpPassword}
                      edge="end"
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
                label="Mật khẩu mới"
                value={password.new}
                onChange={handleInputChange}
                placeholder="Vui lòng nhập mật khẩu mới"
                name="new"
              />
            </FormControl>

            <FormControl
              className="mb-5"
              variant="outlined"
              fullWidth
              size="small"
            >
              <InputLabel htmlFor="outlined-adornment-password">
                Xác nhận mật khẩu mới
              </InputLabel>
              <OutlinedInput
                id="outlined-adornment-password"
                type={showRePassword ? "text" : "password"}
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label={
                        showRePassword
                          ? "hide the password"
                          : "display the password"
                      }
                      onClick={handleClickShowRePassword}
                      onMouseDown={handleMouseDownRePassword}
                      onMouseUp={handleMouseUpRePassword}
                      edge="end"
                    >
                      {showRePassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
                label="Xác nhận mật khẩu mới"
                value={password.reNew}
                onChange={handleInputChange}
                placeholder="Vui lòng nhập lại mật khẩu"
                name="reNew"
              />
            </FormControl>

            <Button
              fullWidth
              disableElevation
              type="submit"
              variant="contained"
              id="btn-reset-password"
              onClick={handleResetPassword}
            >
              ĐẶT LẠI MẬT KHẨU
            </Button>
          </Paper>
        </Container>
      </div>
    </>
  );
};

export default ResetPassword;
