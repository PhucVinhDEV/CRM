"use client";
import { Button } from "@mui/material";
import React, { useRef, useState } from "react";
import "./OTPFormStyle.css";
import { useRouter } from "next/navigation";
import { toast } from "react-toastify";

const OTPForm = () => {
  //State mảng mã otp
  const [otp, setOtp] = useState(Array(6).fill(""));
  const inputRefs = useRef([]);

  //State chuyển trang
  const fowardUrl = useRouter();

  //Function validate
  const validateForm = () => {
    if (otp.length <= 0 || otp[0] === "") {
      toast.warning("Vui lòng nhập mã OTP");
      return false;
    }
    return true;
  };
  console.log(otp);
  //Function xác nhận OTP
  const verifyOtp = () => {
    if (validateForm()) {
      const idToast = toast.loading("Vui lòng chờ...");
      setTimeout(() => {
        toast.update(idToast, {
          render: "Xác thực OTP thành công",
          type: "success",
          isLoading: false,
          autoClose: 5000,
          closeButton: true,
        });
      }, 500);
      console.log(otp.join(""));
      fowardUrl.push("/Auth/ResetPassword");
    }
  };

  //Function xử lý sự kiện change input
  const handleOtpChange = (event, index) => {
    const { value } = event.target;
    const newOtp = [...otp];

    if (value.match(/[0-9]/)) {
      newOtp[index] = value;
      setOtp(newOtp);

      // Move to the next input field if not the last one
      if (index < otp.length - 1) {
        inputRefs.current[index + 1].focus();
      }
    } else if (value === "") {
      // Handle backspace
      newOtp[index] = "";
      setOtp(newOtp);

      if (index > 0) {
        inputRefs.current[index - 1].focus();
      }
    }
  };

  return (
    <div className="otp-container-wrapper" id="otp-container-wrapper">
      <div className="otp-form-container" id="otp-form-container">
        <h2 id="otp-title">Xác nhận OTP</h2>
        <div className="otp-fields-container" id="otp-fields-container">
          {otp.map((value, index) => (
            <input
              key={index}
              type="text"
              maxLength="1"
              className="otp-field"
              ref={(el) => (inputRefs.current[index] = el)}
              value={value}
              onChange={(e) => handleOtpChange(e, index)}
              onKeyDown={(e) => {
                if (e.key === "Backspace" && !value && index > 0) {
                  setTimeout(() => {
                    inputRefs.current[index - 1].focus();
                  }, 0);
                }
              }}
            />
          ))}
        </div>
        <Button
          style={{ width: "85%" }}
          disableElevation
          variant="contained"
          onClick={verifyOtp}
          id="otp-submit-btn"
        >
          Xác nhận
        </Button>
      </div>
    </div>
  );
};

export default OTPForm;
