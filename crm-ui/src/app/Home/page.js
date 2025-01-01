"use client";
import { Button } from "@mui/material";
import React from "react";
import axios from "../../components/Localhost/LocalhostServer";
import { jwtDecode } from "jwt-decode";
import { toast } from "react-toastify";
import { useRouter } from "next/navigation";

const Home = () => {
  //Khởi tạo biến nhận accessToken
  const accessToken = localStorage.getItem("accessToken");
  //Giải mã token
  const decodeToken = jwtDecode(accessToken);
  //Khởi tạo biến chuyển hướng trang
  const fowardUrl = useRouter();

  const handleLogout = async () => {
    const idToast = toast.loading("Vui lòng chờ...");
    try {
      const res = await axios.post(
        `/api/auth/v1/logout?userId=${decodeToken.sub}`
      );
      if (res.data.status === 1000 && !res.data.hasErrors) {
        toast.update(idToast, {
          render: "Đăng xuất thành công",
          type: "success",
          isLoading: false,
          autoClose: 5000,
          closeButton: true,
        });

        //Xóa localSessionStogare
        localStorage.clear();

        //Chuyển hướng trang
        fowardUrl.push('/');
      }
    } catch (error) {
      toast.update(idToast, {
        render: "Lỗi đăng xuất vui lòng thử lại",
        type: "error",
        isLoading: false,
        autoClose: 5000,
        closeButton: true,
      });
      console.log(error);
    }
  };
  return (
    <>
      <div className="text-center">
        <h1 className="text-2xl font-bold">Đăng nhập thành công</h1>
        <Button
          variant="contained"
          disableElevation
          onClick={handleLogout}
          color="warning"
        >
          Đăng xuất
        </Button>
      </div>
    </>
  );
};

export default Home;
