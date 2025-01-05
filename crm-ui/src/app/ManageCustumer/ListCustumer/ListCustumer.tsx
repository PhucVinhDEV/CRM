"use client";
import React from "react";
import {
  Button,
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";
import BorderColorIcon from "@mui/icons-material/BorderColor";
import DeleteIcon from "@mui/icons-material/Delete";

const ListCustumer = () => {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };
  return (
    <>
      <Container className="mb-4">
        <TableContainer component={Paper} className="mb-4">
          <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
            <TableHead>
              <TableRow>
                <TableCell>Họ và tên</TableCell>
                <TableCell align="center">Email</TableCell>
                <TableCell align="center">Giới tính</TableCell>
                <TableCell align="center">Ngày sinh</TableCell>
                <TableCell align="center">Số điện thoại</TableCell>
                <TableCell align="center">Loại khách hàng</TableCell>
                <TableCell align="center">Tình trạng</TableCell>
                <TableCell align="center">Thao tác</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              <TableRow
                // key={row.name}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  Trần Quốc Thịnh
                </TableCell>
                <TableCell align="center">
                  thinhtran24082004@gmail.com
                </TableCell>
                <TableCell align="center">Nam</TableCell>
                <TableCell align="center">24/08/2004</TableCell>
                <TableCell align="center">0845710208</TableCell>
                <TableCell align="center">Cá nhân</TableCell>
                <TableCell align="center">Đang hoạt động</TableCell>
                <TableCell align="center">
                  <div className="flex justify-center space-x-3">
                    <Button className="bg-yellow-100 text-yellow-500">
                      <BorderColorIcon />
                    </Button>
                    <Button className="bg-red-100 text-red-500">
                      <DeleteIcon />
                    </Button>
                  </div>
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          // count={rows.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Container>
    </>
  );
};

export default ListCustumer;
