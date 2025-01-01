"use client";
import React, { useState } from "react";
import "./LoginStyle.css";

import Login from "./Login";
import Register from "./Register";

const FormSign = () => {
  //state chuyển đổi form
  const [isChangeForm, setIsChangeForm] = useState(true);
  return (
    <div className="container-sign">
      <div className="form-sign-container">
        <div className="form-toggle">
          <button
            className={isChangeForm ? "active" : ""}
            onClick={() => setIsChangeForm(true)}
          >
            Đăng nhập
          </button>
          <button
            className={!isChangeForm ? "active" : ""}
            onClick={() => setIsChangeForm(false)}
          >
            Đăng ký
          </button>
        </div>
        {isChangeForm ? (
          <>
            {/* Đăng nhập */}
            <Login setIsChangeForm={setIsChangeForm} />
          </>
        ) : (
          <>
            {/* Đăng ký */}
            <Register />
          </>
        )}
      </div>
     
    </div>
  );
};

export default FormSign;
