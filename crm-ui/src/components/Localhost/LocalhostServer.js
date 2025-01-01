import axios from "axios";

//Khởi tạo cấu hình localserver
const server = axios.create({
  baseURL: "http://localhost:8888/bitznomad/",
  headers: {
    "Content-Type": "application/json",
  },
});

// Sử dụng Interceptor để thêm token vào request
server.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken"); // Lấy token từ localStorage hoặc một nơi khác
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

//Sử dụng Interceptor để kiểm tra token dead
// server.interceptors.response.use(
//   (response) => response,
//   async (error) => {
//     if (error.response && error.response.status === 401) {
//       console.log("Token hết hạn");
//       // Thực hiện refresh token hoặc điều hướng
//     }
//     return Promise.reject(error);
//   }
// );

export default server;
