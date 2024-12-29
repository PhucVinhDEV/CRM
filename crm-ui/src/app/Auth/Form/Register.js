"use client";
import React, { useState } from "react";
import {
  Box,
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  FormLabel,
  IconButton,
  Input,
  InputAdornment,
  InputLabel,
  Radio,
  RadioGroup,
  TextField,
} from "@mui/material";
import PersonAddAltIcon from "@mui/icons-material/PersonAddAlt";
import PhoneAndroidIcon from "@mui/icons-material/PhoneAndroid";
import MailOutlineIcon from "@mui/icons-material/MailOutline";
import KeyIcon from "@mui/icons-material/Key";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { toast } from "react-toastify";

const Register = () => {
  //State hiển thị mật khẩu
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfig, setShowPasswordConfig] = useState(false);
  const [isFocusedPass, setIsFocusedPass] = useState(false);
  const [isFocusedPassCofig, setIsFocusedPassCofig] = useState(false);

  //State dữ liệu đăng ký
  const [formData, setFormData] = useState({
    fullname: "",
    email: "",
    password: "",
    rePassword: "",
    phone: "",
    gender: true,
    check: false,
  });

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  //   const handleMouseUpPassword = (event) => {
  //     event.preventDefault();
  //   };

  const handleClickShowPasswordConfig = () =>
    setShowPasswordConfig((show) => !show);

  const handleMouseDownPasswordConfig = (event) => {
    event.preventDefault();
  };

  //Function xử lý sự kiện inputChange
  const handleInputChange = (event) => {
    const { name, value, type, checked } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  //Function validate
  const validateForm = () => {
    const { fullname, email, password, rePassword, phone, check } = formData;
    const pattentEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const pattentPhone = /0[0-9]{9}/;
    const patternPassword = /^(?=.*[a-zA-Z]).{8,}$/;
    if (!fullname && !email && !password && !rePassword && !phone && !check) {
      toast.warning("Vui lòng nhập đầy đủ các thông tin.");
      return false;
    } else {
      if (fullname === "" || fullname === null) {
        toast.warning("Vui lòng nhập họ và tên.");
        return false;
      }

      if (email === "" || email === null) {
        toast.warning("Vui lòng nhập email.");
        return false;
      } else if (!pattentEmail.test(email)) {
        toast.warning("Email không hợp lệ.");
        return false;
      }

      if (password === "" || password === null) {
        toast.warning("Vui lòng nhập mật khẩu.");
        return false;
      } else if (password.length < 8 || !patternPassword.test(password)) {
        toast.warning(
          "Mật khẩu phải chứa ít nhất 8 ký tự bao gồm chữ hoa hoặc thường"
        );
        return false;
      }

      if (password !== rePassword) {
        toast.warning("Xác thực mật khẩu không khớp");
        return false;
      }

      if (phone === "" || phone === null) {
        toast.warning("Vui lòng nhập số điện thoại");
        return false;
      } else if (!pattentPhone.test(phone)) {
        toast.warning("Số điện thoại không hợp lệ");
        return false;
      }

      if (!check) {
        toast.warning("Bạn chưa chấp nhận điều khoản dịch vụ của chúng tôi.");
        return false;
      }
    }
    return true;
  };

  //Function Register form
  const handleRegister = () => {
    if (validateForm()) {
      console.log(formData);
    }
  };

  return (
    <div className="form-sign">
      <h2 className="mx-2 text-center font-bold text-3xl mt-5">Đăng ký</h2>
      <Box sx={{ display: "flex", alignItems: "flex-end" }} className="mb-3">
        <PersonAddAltIcon sx={{ color: "action.active", mr: 1, my: 0.5 }} />
        <TextField
          id="input-with-sx"
          label="Họ và tên"
          variant="standard"
          fullWidth
          placeholder="Vui lòng nhập họ và tên"
          size="medium"
          name="fullname"
          value={formData.fullname}
          onChange={handleInputChange}
        />
      </Box>
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
          value={formData.email}
          onChange={handleInputChange}
        />
      </Box>

      <div className="flex">
        <div className="flex-1 w-64 me-2">
          <Box
            sx={{ display: "flex", alignItems: "flex-end" }}
            className="mb-3"
          >
            <KeyIcon sx={{ color: "action.active", mr: 1, my: 0.5 }} />
            <FormControl variant="standard" fullWidth>
              <InputLabel htmlFor="enterPass-adornment-password">
                Nhập mật khẩu
              </InputLabel>
              <Input
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                id="enterPass-adornment-password"
                type={showPassword ? "text" : "password"}
                onFocus={() => setIsFocusedPass(true)}
                onBlur={() => setIsFocusedPass(false)}
                endAdornment={
                  isFocusedPass && (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleClickShowPassword}
                        onMouseDown={handleMouseDownPassword}
                      >
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  )
                }
              />
            </FormControl>
          </Box>
        </div>
        <div className="flex-1 w-64 mx-2">
          <Box
            sx={{ display: "flex", alignItems: "flex-end" }}
            className="mb-3"
          >
            <KeyIcon sx={{ color: "action.active", mr: 1, my: 0.5 }} />
            <FormControl variant="standard" fullWidth>
              <InputLabel htmlFor="enterConfig-adornment-password">
                Xác thực mật khẩu
              </InputLabel>
              <Input
                name="rePassword"
                value={formData.rePassword}
                onChange={handleInputChange}
                id="enterConfig-adornment-password"
                type={showPasswordConfig ? "text" : "password"}
                onFocus={() => setIsFocusedPassCofig(true)}
                onBlur={() => setIsFocusedPassCofig(false)}
                endAdornment={
                  isFocusedPassCofig && (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleClickShowPasswordConfig}
                        onMouseDown={handleMouseDownPasswordConfig}
                      >
                        {showPasswordConfig ? (
                          <VisibilityOff />
                        ) : (
                          <Visibility />
                        )}
                      </IconButton>
                    </InputAdornment>
                  )
                }
              />
            </FormControl>
          </Box>
        </div>
      </div>

      <Box sx={{ display: "flex", alignItems: "flex-end" }} className="mb-3">
        <PhoneAndroidIcon sx={{ color: "action.active", mr: 1, my: 0.5 }} />
        <TextField
          id="input-with-sx"
          label="Số điện thoại"
          variant="standard"
          fullWidth
          placeholder="Vui lòng nhập số điện thoại"
          size="medium"
          name="phone"
          value={formData.phone}
          onChange={handleInputChange}
        />
      </Box>

      <FormControl className="mb-3">
        <FormLabel id="demo-row-radio-buttons-group-label">Giới tính</FormLabel>
        <RadioGroup
          row
          aria-labelledby="demo-row-radio-buttons-group-label"
          name="row-radio-buttons-group"
        >
          <FormControlLabel
            value={true}
            checked={formData.gender === true}
            onChange={handleInputChange}
            control={<Radio />}
            label="Nam"
          />
          <FormControlLabel
            value={false}
            checked={formData.gender === false}
            onChange={handleInputChange}
            control={<Radio />}
            label="Nữ"
          />
        </RadioGroup>
      </FormControl>
      <div className="mb-3">
        <Checkbox
          name="check"
          checked={formData.check}
          onChange={handleInputChange}
        />
        <label>
          Tôi đồng ý với các <a>điều khoản và dịch vụ</a>.
        </label>
      </div>
      <div className="mb-3">
        <Button className="btn-sign" fullWidth onClick={handleRegister}>
          Đăng ký
        </Button>
      </div>
    </div>
  );
};

export default Register;
