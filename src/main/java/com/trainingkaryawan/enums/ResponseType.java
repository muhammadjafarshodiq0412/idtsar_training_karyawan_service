package com.trainingkaryawan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseType {
    SUCCESS_PING(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Pong! 👋", "Pong! 👋"),
    SUCCESS_SAVE(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data has been successfully saved", "Data berhasil disimpan"),
    SUCCESS_UPDATE(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data has been successfully updated", "Data berhasil diperbarui"),
    SUCCESS_DELETE(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data has been successfully deleted", "Data berhasil dihapus"),
    SUCCESS_DATA_FOUND(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data found", "Data ditemukan"),

    UNAUTHORIZED_TOKEN_EMPTY(ResponseStatus.UNAUTHORIZED.getCode(), ResponseStatus.UNAUTHORIZED, "Authorization token is empty", "Token otorisasi kosong"),
    UNAUTHORIZED_TOKEN_INVALID(ResponseStatus.UNAUTHORIZED.getCode(), ResponseStatus.UNAUTHORIZED, "Invalid Token", "Token tidak valid"),

    FAILED_SAVE(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Data failed to save", "Data gagal disimpan"),
    FAILED_UPDATE(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Data failed to update", "Data gagal diperbarui"),
    FAILED_DELETE(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Data failed to delete", "Data gagal dihapus"),

    DATA_NOT_FOUND(ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND, "%s not found", "%s tidak ditemukan"),
    USER_NOT_ACTIVATED(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "User has not been activated", "Pengguna belum aktif"),
    INCORRECT_CREDENTIAL(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Incorrect username or password!", "Nama pengguna atau kata sandi salah!"),

    TIME_OUT(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Time Out", "Time Out"),
    ERROR_SAVING(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when saving data", "Gagal pada saat simpan data"),
    ERROR_UPDATING(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when update data", "Gagal pada saat perbaharui data"),
    ERROR_DELETING(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when delete data", "Gagal pada saat menghapus data"),
    ERROR_FIND_DATA(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when get data", "Gagal pada saat mengambil data"),
    INTERNAL_SERVER_ERROR(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Internal Server Error", "Kesalahan server internal"),
    UNKNOWN_ERROR(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Unknown Error", "Error Tidak Diketahui");

    private int messageCode;
    private ResponseStatus responseStatus;
    private String descriptionEn;
    private String descriptionId;
}
