/* eslint-disable @next/next/no-img-element */
"use client";
import React, { useState } from "react";
import {
  Button,
  FormControl,
  IconButton,
  InputAdornment,
  InputLabel,
  OutlinedInput,
} from "@mui/material";

import "./ResetPasswordStyle.css";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { toast } from "react-toastify";
import Image from "next/image";
import axios from "../../../components/Localhost/LocalhostServer";
import { useRouter } from "next/navigation";

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

  //Biến xử lý querry
  const params = new URLSearchParams(window.location.search);
  const tokenFromUrl = params.get("token");

  //Function reset password
  const handleResetPassword = async () => {
    if (validateForm()) {
      const idToast = toast.loading("Vui lòng chờ...");
      try {
        await axios.post(
          `/change-password?reNewPassword=${password.reNew}&newPassword=${password.new}&token=${tokenFromUrl}`,
          {
            reNewPassword: password.reNew,
            newPassword: password.new,
            token: tokenFromUrl,
          }
        );
        toast.update(idToast, {
          render: "Đổi mật khẩu thành công",
          type: "success",
          isLoading: false,
          autoClose: 5000,
          closeButton: true,
        });
        console.log(password);
        fowardUrl.push("/Auth/Form");
      } catch (error) {
        toast.update(idToast, {
          render: "Đổi mật khẩu thất bại",
          type: "error",
          isLoading: false,
          autoClose: 5000,
          closeButton: true,
        });
        console.log(error);
      }
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="flex flex-col md:flex-row w-11/12 md:w-3/4 lg:w-2/3 bg-white shadow-lg rounded-lg overflow-hidden">
        {/* Left Column */}
        <div className="flex flex-col items-center justify-center w-full md:w-1/2 p-6 bg-gray-50">
          <div className="flex items-center gap-4 mb-6">
            <Image
              src="/logoWeb/Infinity.png"
              alt="Logo"
              width={100}
              height={50}
              className="rounded-full"
            />
            <h1 className="text-2xl font-bold">Việc làm dễ</h1>
          </div>

          <div className="w-full max-w-md space-y-6">
            <h2 className="text-xl font-semibold text-center">
              Thay đổi mật khẩu
            </h2>

            {/* New Password */}
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

            {/* Confirm Password */}
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
              type="submit"
              variant="contained"
              fullWidth
              className="py-2 mt-4"
              onClick={handleResetPassword}
              id="btn-reset-password"
            >
              Xác nhận
            </Button>
          </div>
        </div>

        {/* Right Column */}
        <div className="hidden md:block w-1/2">
          <img
            src="https://www.chas.co.uk/wp-content/uploads/2021/09/How-to-Find-Work-for-Your-Construction-Company.jpg"
            alt="Decorative Image"
            className="h-full w-full object-cover"
          />
        </div>
      </div>
    </div>
  );
};

export default ResetPassword;
