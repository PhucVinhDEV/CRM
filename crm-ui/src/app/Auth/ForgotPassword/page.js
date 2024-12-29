"use client";
import { Button, Container, Paper, TextField } from "@mui/material";
import Image from "next/image";
import React, { useState } from "react";
import "./ForgotPasswordStyle.css";
import Link from "next/link";
import { toast } from "react-toastify";
import { useRouter } from "next/navigation";

const ForgotPassword = () => {
  //Khởi tạo useRouter
  const forward = useRouter();
  //State dữ liệu
  const [email, setEmail] = useState("");

  //Function xử lý sự kiện input change
  const handleInputChange = (e) => {
    const value = e.target.value;
    setEmail(value);
  };

  //Function validate
  const validateForm = () => {
    const pattentEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email === "" || email === null) {
      toast.warning("Vui lòng nhập Email.");
      return false;
    } else if (!pattentEmail.test(email)) {
      toast.warning("Email không hợp lệ.");
      return false;
    }
    return true;
  };

  //Function sử lý forgot
  const handleForgotPass = () => {
    if (validateForm()) {
      const idToast = toast.loading("Vui lòng chờ...");
      setTimeout(() => {
        toast.update(idToast, {
          render: "Vui lòng kiểm tra email để lấy mã OTP",
          type: "success",
          isLoading: false,
          autoClose: 5000,
          closeButton: true,
        });
      }, 500);
      console.log(email);
      forward.push("/Auth/OTPForm");
    }
  };
  return (
    <>
      {/* chế độ lưới canh giữa chiều ngang và dọc đặt chiều cao của phần tử bao ngoài bằng toàn bộ chiều cao màn hình */}
      <div className="grid place-items-center h-screen">
        <Container maxWidth="sm">
          <Paper className="p-5">
            <Image
              src="/logoWeb/Infinity.png"
              alt=""
              width={200}
              height={50}
              className="object-cover h-100 w-100"
            />
            <div className="text-center mb-5">
              <h2 className="font-bold text-xl mb-2">Quên mật khẩu?</h2>
              <label htmlFor="">
                Nhập email đã tạo tài khoản để nhận OTP thay đổi mật khẩu
              </label>
            </div>
            <div className="mb-5">
              <TextField
                id="outlined-basic"
                label="Email"
                variant="outlined"
                name="email"
                value={email}
                onChange={handleInputChange}
                fullWidth
                size="medium"
                placeholder="Vui lòng nhập email"
              />
            </div>
            <Button
              className="btn-forgot mb-3"
              fullWidth
              onClick={handleForgotPass}
            >
              Lấy lại mật khẩu
            </Button>
            <div className="text-center">
              <Link
                href={"/Auth/Form"}
                className="hover:underline"
                style={{ color: "#033452" }}
              >
                Quay về trang đăng nhập
              </Link>
            </div>
          </Paper>
        </Container>
      </div>
    </>
  );
};

export default ForgotPassword;
