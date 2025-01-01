"use client";
import React, { useState } from "react";
import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import {
  Box,
  Button,
  FormControl,
  IconButton,
  Input,
  InputAdornment,
  InputLabel,
  TextField,
} from "@mui/material";
import KeyIcon from "@mui/icons-material/Key";
import MailOutlineIcon from "@mui/icons-material/MailOutline";
import Link from "next/link";
import { toast } from "react-toastify";
import axios from "../../../components/Localhost/LocalhostServer";
import { useRouter } from "next/navigation";

const Login = ({ setIsChangeForm }) => {
  //State hiển thị mật khẩu
  const [showPassword, setShowPassword] = useState(false);
  //   const [isFocusedPass, setIsFocusedPass] = useState(false);
  //State nhận trường dữ liệu
  const [dataForm, setDataForm] = useState({
    email: "",
    password: "",
  });
  //Khởi tạo biến chuyển trang
  const fowardUrl = useRouter();

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const handleMouseUpPassword = (event) => {
    event.preventDefault();
  };

  //Function xử lý input change
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setDataForm({
      ...dataForm,
      [name]: value,
    });
  };

  //Function validate
  const validateForm = () => {
    if (
      (dataForm.email === "" || dataForm.email === null) &&
      (dataForm.password === "" || dataForm.password === null)
    ) {
      toast.warning("Vui lòng nhập đầy đủ các thông tin.");
      return false;
    } else {
      if (dataForm.email === "" || dataForm.email === null) {
        toast.warning("Vui lòng nhập email.");
        return false;
      }
      if (dataForm.password === "" || dataForm.password === null) {
        toast.warning("Vui lòng nhập mật khẩu.");
        return false;
      }
    }

    return true;
  };

  //Function xử lý submit
  const handleLogin = async () => {
    if (validateForm()) {
      const email = dataForm.email;
      const password = dataForm.password;
      const idToast = toast.loading("Vui lòng chờ...");
      try {
        const res = await axios.post(
          `api/auth/v1/login?username=${email}&password=${password}`
        );
        if (res.data.status === 1000 && !res.data.hasErrors) {
          toast.update(idToast, {
            render: "Đăng nhập thành công",
            type: "success",
            isLoading: false,
            autoClose: 5000,
            closeButton: true,
          });
          fowardUrl.push("/Home");
          localStorage.setItem(
            "accessToken",
            res.data.result.token.access_token
          );
          localStorage.setItem(
            "refreshToken",
            res.data.result.token.refresh_token
          );
        }
      } catch (error) {
        console.log(error);
        if (
          error.response?.data.status === 500 &&
          error.response.data.hasErrors
        ) {
          toast.update(idToast, {
            render: error.response.data.errors[0],
            type: "warning",
            isLoading: false,
            autoClose: 5000,
            closeButton: true,
          });
        } else {
          toast.update(idToast, {
            render: "Lỗi máy chủ vui lòng thử lại sau",
            type: "error",
            isLoading: false,
            autoClose: 5000,
            closeButton: true,
          });
        }
      }
    }
  };
  return (
    <div className="form-sign">
      <h2 className="mx-2 text-center font-bold text-3xl mt-10">Đăng nhập</h2>
      <Box sx={{ display: "flex", alignItems: "flex-end" }} className="mb-3">
        <MailOutlineIcon sx={{ color: "action.active", mr: 1, my: 0.5 }} />
        <TextField
          id="input-with-sx"
          label="Email"
          variant="standard"
          fullWidth
          placeholder="Vui lòng nhập email"
          size="medium"
          name="email"
          value={dataForm.email}
          onChange={handleInputChange}
        />
      </Box>

      <Box sx={{ display: "flex", alignItems: "flex-end" }} className="mb-3">
        <KeyIcon sx={{ color: "action.active", mr: 1, my: 0.5 }} />
        <FormControl variant="standard" fullWidth size="medium">
          <InputLabel htmlFor="outlined-adornment-password">
            Mật khẩu
          </InputLabel>
          <Input
            id="outlined-adornment-password"
            type={showPassword ? "text" : "password"}
            placeholder="Vui lòng nhập mật khẩu"
            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label={
                    showPassword ? "hide the password" : "display the password"
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
            label="Password"
            size="medium"
            name="password"
            value={dataForm.password}
            onChange={handleInputChange}
          />
        </FormControl>
      </Box>
      <div className="mb-3 text-end">
        <Link href="/Auth/ForgotPassword" className="hover:underline">
          Quên mật khẩu?
        </Link>
      </div>
      <div className="mb-3">
        <Button className="btn-sign" fullWidth onClick={handleLogin}>
          Đăng nhập
        </Button>
      </div>
      <div className="mb-3 text-center">
        <label htmlFor="">Bạn chưa có tài khoản? </label>
        <Link
          href=""
          onClick={(e) => {
            e.preventDefault(); // Ngăn trang reload
            setIsChangeForm(false);
          }}
          className="hover:underline"
        >
          Đăng ký ngay.
        </Link>
      </div>
      <label htmlFor="" className="text-center mb-3">
        &#8212;&#8212;&#8212;&#8212;&#8212;Hoặc&#8212;&#8212;&#8212;&#8212;&#8212;
      </label>
      <div className="mb-2">
        <GoogleOAuthProvider clientId="YOUR_GOOGLE_CLIENT_ID">
          <GoogleLogin
            //   theme={mode === "light" ? "outline" : "filled_black"}
            //   onSuccess={handleLoginByGoogle}
            onError={() => {
              console.log("Login Failed");
            }}
          />
        </GoogleOAuthProvider>
      </div>
    </div>
  );
};

export default Login;
