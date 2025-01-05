"use client";
import { CustumerType } from "../../../DataType/DataType";
import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import {
  Button,
  Container,
  FormControl,
  FormControlLabel,
  FormLabel,
  InputLabel,
  MenuItem,
  Radio,
  RadioGroup,
  Select,
  SelectChangeEvent,
} from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, { Dayjs } from "dayjs";
import FormAddressSelect from "@/components/APIAddressVN/FormAddressSelect";
import { toast } from "react-toastify";
const CreateCustumer = () => {
  const [formData, setFormData] = useState<CustumerType>({
    firstName: "",
    lastName: "",
    email: "",
    gender: true,
    birthDay: null,
    address: "",
    phone: "",
    customerType: "",
    profession: "",
    statusCustumer: "",
    postalCode: "",
    nameConpany: "",
    jobRole: "",
  });

  const [inforAddress, setInfoAddress] = useState<string>("");

  const handleInputChange = (
    e:
      | React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
      | SelectChangeEvent
  ) => {
    const { name, value } = e.target as
      | HTMLInputElement
      | HTMLTextAreaElement
      | { name?: string; value: unknown };

    setFormData({ ...formData, [name!]: value });
  };

  const handleDateChange = (date: Dayjs | null) => {
    setFormData({ ...formData, birthDay: date ? date : null });
  };

  const validateForm = () => {
    const {
      firstName,
      lastName,
      email,
      birthDay,
      address,
      phone,
      customerType,
      statusCustumer,
    } = formData;
    const patternEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const patternPhone = /^0\d{9}$/;

    if (
      !firstName &&
      !lastName &&
      !email &&
      !birthDay &&
      !address &&
      !phone &&
      !customerType &&
      !statusCustumer
    ) {
      toast.warning("Vui lòng điền đầy đủ thông tin.");
      return false;
    } else {
      if (firstName.trim() === "") {
        toast.warning("Vui lòng nhập họ của khách hàng");
        return false;
      }

      if (lastName.trim() === "") {
        toast.warning("Vui lòng nhập tên của khách hàng");
        return false;
      }

      if (email.trim() === "") {
        toast.warning("Vui lòng nhập email của khách hàng");
        return false;
      } else if (!patternEmail.test(email)) {
        toast.warning("Email không hợp lệ");
        return false;
      }

      if (phone.trim() === "") {
        toast.warning("Vui lòng nhập số điện thoại của khách hàng");
        return false;
      } else if (!patternPhone.test(phone)) {
        toast.warning(
          "Số điện thoại không hợp lệ (phải bắt đầu bằng số 0 và có 10 chữ số)"
        );
        return false;
      }

      if (!birthDay) {
        toast.warning("Vui lòng nhập ngày sinh");
        return false;
      } else {
        const today = dayjs(); // Ngày hiện tại với đầy đủ thông tin giờ phút
        const birthDate = dayjs(birthDay); // Ngày sinh với đầy đủ thông tin giờ phút

        const age = today.diff(birthDate, "year");

        // Kiểm tra ngày sinh bằng hoặc sau ngày hiện tại
        if (
          birthDate.isSame(today, "day") ||
          birthDate.isSame(today, "month") ||
          birthDate.isAfter(today)
        ) {
          toast.warning("Ngày sinh không thể lớn hơn hoặc bằng ngày hiện tại");
          return false;
        }

        // Kiểm tra tuổi tối đa
        if (age > 100) {
          toast.warning("Tuổi không hợp lệ (quá 100 tuổi)");
          return false;
        }
      }

      if (customerType.trim() === "") {
        toast.warning("Vui lòng chọn loại khách hàng");
        return false;
      }

      if (statusCustumer.trim() === "") {
        toast.warning("Vui lòng chọn tình trạng khách hàng");
        return false;
      }

      if (address.trim() === "") {
        toast.warning("Vui lòng nhập số nhà");
        return false;
      }
    }

    return true;
  };

  const handleSubmit = () => {
    if (validateForm()) {
      console.log("Submitted data:", {
        ...formData,
        birthDay: dayjs(formData.birthDay).format("DD/MM/YYYY"),
        address: formData.address + " " + inforAddress,
      });
    }

    // Reset form if needed
  };
  return (
    <>
      <Container className="mb-4">
        <div className="flex space-x-3 mb-4 ">
          <div className="flex-1 w-80 ">
            <h2 className="text-xl font-semibold mb-4">Thông tin cơ bản</h2>

            {/* Họ khách hàng */}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  <span>
                    Họ <span className="text-red-600">(*)</span>
                  </span>
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder="Vui lòng nhập họ của khách hàng"
                name="firstName"
                value={formData.firstName}
                onChange={handleInputChange}
              />
            </div>

            {/* Tên khách hàng */}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  <span>
                    Tên <span className="text-red-600">(*)</span>
                  </span>
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder="Vui lòng nhập tên của khách hàng"
                name="lastName"
                value={formData.lastName}
                onChange={handleInputChange}
              />
            </div>

            {/* Email */}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  <span>
                    Email <span className="text-red-600">(*)</span>
                  </span>
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder="Vui lòng nhập email của khách hàng"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
              />
            </div>

            {/* Số điện thoại */}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  <span>
                    Số điện thoại <span className="text-red-600">(*)</span>
                  </span>
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder="Vui lòng nhập số điện thoại của khách hàng"
                name="phone"
                value={formData.phone}
                onChange={handleInputChange}
              />
            </div>

            {/* Ngày sinh */}
            <div className="mb-4">
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  format="DD/MM/YYYY"
                  name="birthdate"
                  value={formData.birthDay ? dayjs(formData.birthDay) : null} // Chuyển đổi múi giờ về Việt Nam
                  onChange={handleDateChange}
                  sx={{
                    "& .MuiInputBase-root": {
                      width: "217%",
                      height: "40px",
                    },
                  }}
                />
              </LocalizationProvider>
            </div>

            {/* giới tính */}
            <div className="mb-4">
              <FormControl>
                <FormLabel id="demo-row-radio-buttons-group-label">
                  Giới tính <span className="text-red-600">(*)</span>
                </FormLabel>
                <RadioGroup
                  row
                  aria-labelledby="demo-row-radio-buttons-group-label"
                  name="row-radio-buttons-group"
                  onChange={(e) => {
                    const genderValue = e.target.value;
                    setFormData({
                      ...formData,
                      gender:
                        genderValue === "true"
                          ? true
                          : genderValue === "false"
                          ? false
                          : null,
                    });
                  }}
                >
                  <FormControlLabel
                    value="true"
                    control={<Radio />}
                    label="Nam"
                    checked={formData.gender === true}
                  />
                  <FormControlLabel
                    value="false"
                    control={<Radio />}
                    label="Nữ"
                    checked={formData.gender === false}
                  />
                  <FormControlLabel
                    value="null"
                    control={<Radio />}
                    label="Khác"
                    checked={formData.gender === null}
                  />
                </RadioGroup>
              </FormControl>
            </div>
          </div>
          <div className="border-r-2 border-gray-400"></div>
          <div className="flex-1 w-50">
            <h2 className="text-xl font-semibold mb-4">Thông tin khách hàng</h2>
            {/* Loại khách hàng */}
            <div className="mb-4">
              <FormControl fullWidth size="small">
                <InputLabel id="demo-simple-select-label">
                  Loại khách hàng <span className="text-red-600">(*)</span>
                </InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  name="customerType"
                  value={formData.customerType}
                  label={
                    <span>
                      Loại khách hàng <span className="text-red-600">(*)</span>
                    </span>
                  }
                  onChange={handleInputChange}
                >
                  <MenuItem value={"Cá nhân"}>Cá nhân</MenuItem>
                  <MenuItem value={"Doanh nghiệp"}>Doanh nghiệp</MenuItem>
                </Select>
              </FormControl>
            </div>

            {/* Trạng thái */}
            <div className="mb-4">
              <FormControl fullWidth size="small">
                <InputLabel id="demo-simple-select-label">
                  Tình trạng khách hàng{" "}
                  <span className="text-red-600">(*)</span>
                </InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  name="statusCustumer"
                  value={formData.statusCustumer}
                  label={
                    <span>
                      Tình trạng khách hàng{" "}
                      <span className="text-red-600">(*)</span>
                    </span>
                  }
                  onChange={handleInputChange}
                >
                  <MenuItem value={"Đang hoạt động"}>Đang hoạt động</MenuItem>
                  <MenuItem value={"TIềm năng"}>Tiềm năng</MenuItem>
                  <MenuItem value={"Ngừng hoạt động"}>Ngừng hoạt động</MenuItem>
                </Select>
              </FormControl>
            </div>

            {/* Tên công ty*/}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  formData.customerType === "Cá nhân"
                    ? "Loại khách hàng không thể thao tác"
                    : "Tên công ty"
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder={"Vui lòng nhập tên công ty"}
                name="nameConpany"
                value={formData.nameConpany}
                onChange={handleInputChange}
                disabled={formData.customerType === "Cá nhân"}
              />
            </div>

            {/* Ngành nghề (Tùy chọn) */}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  formData.customerType === "Cá nhân"
                    ? "Loại khách hàng không thể thao tác"
                    : "Ngành nghề (Chỉ với các doanh nghiệp)"
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder="Vui lòng nhập ngành nghề của khách hàng"
                name="profession"
                value={formData.profession}
                onChange={handleInputChange}
                disabled={formData.customerType === "Cá nhân"}
              />
            </div>

            {/* Vai trò công việc (Tùy chọn) */}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  formData.customerType === "Cá nhân"
                    ? "Loại khách hàng không thể thao tác"
                    : "Vai trò công việc"
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder="Vui lòng nhập vai trò"
                name="jobRole"
                value={formData.jobRole}
                onChange={handleInputChange}
                disabled={formData.customerType === "Cá nhân"}
              />
            </div>

            {/* Mã bưu điện */}
            <div className="mb-4">
              <TextField
                id="outlined-basic"
                label={
                  formData.customerType === "Cá nhân"
                    ? "Loại khách hàng không thể thao tác"
                    : "Mã bưu điện"
                }
                variant="outlined"
                fullWidth
                size="small"
                placeholder="Vui lòng nhập mã bưu điện"
                name="postalCode"
                value={formData.postalCode}
                onChange={handleInputChange}
                disabled={formData.customerType === "Cá nhân"}
              />
            </div>
          </div>
        </div>
        {/* Địa chỉ */}
        <div className="mb-4">
          <label htmlFor="address" className="block font-medium mb-4">
            Địa chỉ <span className="text-red-600">(*)</span>
          </label>
          <FormAddressSelect apiAddress={setInfoAddress} />
        </div>
        <div className="mb-4">
          <TextField
            id="outlined-multiline-static"
            label="Số nhà"
            placeholder="Vui lòng nhập số nhà ví dụ: Số 123, Đường ABC"
            multiline
            rows={4}
            fullWidth
            name="address"
            value={formData.address}
            onChange={handleInputChange}
          />
        </div>

        {/* Nút Submit */}
        <Button
          type="submit"
          fullWidth
          size="small"
          onClick={handleSubmit}
          variant="contained"
          className="bg-green-100 text-green-500"
          disableElevation
        >
          {/* {mode === "create" ? "Thêm khách hàng" : "Cập nhật khách hàng"} */}{" "}
          Thêm
        </Button>
      </Container>
    </>
  );
};

export default CreateCustumer;
