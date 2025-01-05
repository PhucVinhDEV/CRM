import { Dayjs } from "dayjs"

//Dữ liệu của form quản lý khách hàng
export type CustumerType = {
    firstName: string,
    lastName: string,
    email: string
    gender: boolean | null,
    birthDay: Dayjs | null,
    address : string,
    phone: string,
    customerType : string,
    profession: string,
    statusCustumer: string,
    postalCode: string,
    nameConpany: string,
    jobRole: string
}