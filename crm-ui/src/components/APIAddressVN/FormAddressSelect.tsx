"use client";
import React, { useEffect, useState } from 'react';
import { APITinhThanh } from '../Localhost/LocalhostServer';
import { FormControl, Grid, InputLabel, MenuItem, Select, SelectChangeEvent } from '@mui/material';

interface Province {
    code: string;
    name: string;
}

interface District {
    code: string;
    name: string;
}

interface Ward {
    code: string;
    name: string;
}



interface FormAddressSelectProps {
    apiAddress?: (address: string) => void;
    resetForm?: boolean;
    editFormAddress?: string;
}

const FormAddressSelect: React.FC<FormAddressSelectProps> = ({ apiAddress, resetForm, editFormAddress }) => {
    const [provinces, setProvinces] = useState<Province[]>([]);
    const [districts, setDistricts] = useState<District[]>([]);
    const [wards, setWards] = useState<Ward[]>([]);

    const [selectedProvince, setSelectedProvince] = useState<string>("");
    const [selectedDistrict, setSelectedDistrict] = useState<string>("");
    const [selectedWard, setSelectedWard] = useState<string>("");

    const [nameProvinces, setNameProvinces] = useState<string>("");
    const [nameDistricts, setNameDistricts] = useState<string>("");
    const [nameWards, setNameWards] = useState<string>("");

    const loadProvinces = async () => {
        try {
            const res = await APITinhThanh.get<{ data: Province[] }>(`api/p/`);
            setProvinces(res.data);
        } catch (error) {
            console.error(error);
        }
    };

    const updateResultAddress = async () => {
        try {
            const province = await APITinhThanh.get<{ data: Province }>(`/api/p/${selectedProvince}`);
            const district = await APITinhThanh.get<{ data: District }>(`/api/d/${selectedDistrict}`);
            const ward = await APITinhThanh.get<{ data: Ward }>(`/api/w/${selectedWard}`);

            apiAddress(`${ward.data.name}, ${district.data.name}, ${province.data.name}`);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadProvinces();
    }, []);

    useEffect(() => {
        if (selectedProvince && selectedDistrict && selectedWard) {
            updateResultAddress();
        }
    }, [selectedProvince, selectedDistrict, selectedWard]);

    useEffect(() => {
        if (resetForm) {
            setSelectedProvince("");
            setSelectedDistrict("");
            setSelectedWard("");
            setDistricts([]);
            setWards([]);
            apiAddress("");
        }
    }, [resetForm, apiAddress]);

    useEffect(() => {
        const splitName = editFormAddress?.split(",").map((item) => item.trim());
        if (splitName?.length === 3) {
            setNameWards(splitName[0]);
            setNameDistricts(splitName[1]);
            setNameProvinces(splitName[2]);
        }

        const loadEditAddress = async () => {
            try {
                const provinceRes = await APITinhThanh.get<{ data: Province[] }>(`api/p/search/?q=${nameProvinces}`);
                const foundProvince = provinceRes.data.find((p: { name: string; }) => p.name === nameProvinces);


                if (foundProvince) {
                    setSelectedProvince(foundProvince.code);

                    const districtRes = await APITinhThanh.get<{ data: { districts: District[] } }>(
                        `api/p/${foundProvince.code}?depth=2`
                    );
                    setDistricts(districtRes.data.districts);

                    const foundDistrict = districtRes.data.districts.find((d) => d.name === nameDistricts);
                    if (foundDistrict) {
                        setSelectedDistrict(foundDistrict.code);

                        const wardRes = await APITinhThanh.get<{ data: { wards: Ward[] } }>(
                            `api/d/${foundDistrict.code}?depth=2`
                        );
                        setWards(wardRes.data.data.wards);

                        const foundWard = wardRes.data.wards.find((w) => w.name === nameWards);
                        if (foundWard) {
                            setSelectedWard(foundWard.code);
                        }
                    }
                }
            } catch (error) {
                console.error("Error loading edit address:", error);
            }
        };

        loadEditAddress();
    }, [editFormAddress, nameProvinces, nameDistricts, nameWards]);

    const handleChangeProvinces = async (e: SelectChangeEvent<string>) => {
        const id = e.target.value; // Giờ đây, `e.target.value` đã có kiểu string
        setSelectedProvince(id);
        setSelectedDistrict("");
        setSelectedWard("");
        setDistricts([]);
        setWards([]);

        if (id) {
            try {
                const res = await APITinhThanh.get<{ data: { districts: District[] } }>(`api/p/${id}?depth=2`);
                setDistricts(res.data.districts); // Sửa lại đúng với kiểu dữ liệu trả về
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleChangeDistricts = async (e: SelectChangeEvent<string>) => {
        const id = e.target.value;
        setSelectedDistrict(id);
        setSelectedWard("");
        setWards([]);

        if (id) {
            try {
                const res = await APITinhThanh.get<{ data: { wards: Ward[] } }>(`api/d/${id}?depth=2`);
                setWards(res.data.wards);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleChangeWards = (e: SelectChangeEvent<string>) => {
        setSelectedWard(e.target.value);
    };

    return (
        <Grid container spacing={3}>
            <Grid item xs={12} sm={12}>
                <FormControl fullWidth size="small" sx={{ textAlign: "left" }}>
                    <InputLabel id="province-select-label">Tỉnh / Thành</InputLabel>
                    <Select
                        labelId="province-select-label"
                        id="province-select"
                        value={selectedProvince}
                        onChange={handleChangeProvinces}
                        label="Tỉnh / Thành"
                    >
                        <MenuItem value="">
                            <em>Tỉnh / Thành</em>
                        </MenuItem>
                        {provinces.map((province) => (
                            <MenuItem key={province.code} value={province.code}>
                                {province.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </Grid>

            <Grid item xs={12} sm={12}>
                <FormControl
                    fullWidth
                    size="small"
                    sx={{ textAlign: "left" }}
                    disabled={districts.length === 0}
                >
                    <InputLabel id="district-select-label">Quận / Huyện</InputLabel>
                    <Select
                        labelId="district-select-label"
                        id="district-select"
                        value={selectedDistrict}
                        onChange={handleChangeDistricts}
                        label="Quận / Huyện"
                    >
                        <MenuItem value="">
                            <em>Quận / Huyện</em>
                        </MenuItem>
                        {districts.map((district) => (
                            <MenuItem key={district.code} value={district.code}>
                                {district.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </Grid>

            <Grid item xs={12} sm={12}>
                <FormControl
                    fullWidth
                    size="small"
                    sx={{ textAlign: "left" }}
                    disabled={wards.length === 0}
                >
                    <InputLabel id="ward-select-label">Phường / Xã</InputLabel>
                    <Select
                        labelId="ward-select-label"
                        id="ward-select"
                        value={selectedWard}
                        onChange={handleChangeWards}
                        label="Phường / Xã"
                    >
                        <MenuItem value="">
                            <em>Phường / Xã</em>
                        </MenuItem>
                        {wards.map((ward) => (
                            <MenuItem key={ward.code} value={ward.code}>
                                {ward.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </Grid>
        </Grid>
    );
};

export default FormAddressSelect;
