package com.trainingkaryawan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseType {
    SUCCESS_PING(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Pong! 👋", "Pong! 👋"),
    SUCCESS_GENERATE_TOKEN(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Token generated", "Token berhasil digenerate"),
    SUCCESS_SAVE(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data has been successfully saved", "Data berhasil disimpan"),
    SUCCESS_UPDATE(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data has been successfully updated", "Data berhasil diperbarui"),
    SUCCESS_DELETE(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data has been successfully deleted", "Data berhasil dihapus"),
    SUCCESS_DATA_FOUND(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Data found", "Data ditemukan"),
    SUCCESS_VALIDATE_OTP(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Success validate otp", "Sukses validasi otp"),
    SUCCESS_SEND_OTP(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Success send otp", "Sukses kirim otp"),
    SUCCESS_CHANGE_PASSWORD(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Success change password", "Sukses ganti kata sandi"),
    SUCCESS_UPLOAD_FILE(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, "Success upload file", "Sukses unggah file"),

    UNAUTHORIZED_TOKEN_EMPTY(ResponseStatus.UNAUTHORIZED.getCode(), ResponseStatus.UNAUTHORIZED, "Authorization token is empty", "Token otorisasi kosong"),
    UNAUTHORIZED_TOKEN_INVALID(ResponseStatus.UNAUTHORIZED.getCode(), ResponseStatus.UNAUTHORIZED, "Invalid Token", "Token tidak valid"),

    FAILED_SAVE(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Data failed to save", "Data gagal disimpan"),
    CREDENTIAL_AUTH_WORNG(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Username or password is wrong", "Username atau password salah"),
    FAILED_UPDATE(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Data failed to update", "Data gagal diperbarui"),
    FAILED_DELETE(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Data failed to delete", "Data gagal dihapus"),
    FAILED_VALIDATE_OTP_EXPIRED(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Otp expired", "Otp sudah kadaluarsa"),
    FAILED_VALIDATE_OTP_NOT_VERIFIED(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Otp not verified", "Otp belum di verifikasi"),
    FAILED_PASSWORD_NOT_MATCH(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Password not match", "Kata sandi tidak sama"),
    FAILED_SEND_OTP(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Failed send otp", "Gagal kirim otp"),

    DATA_NOT_FOUND(ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND, "%s not found", "%s tidak ditemukan"),
    USER_NOT_ACTIVATED(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "User has not been activated", "Pengguna belum aktif"),
    INCORRECT_CREDENTIAL(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST, "Incorrect username or password!", "Nama pengguna atau kata sandi salah!"),

    TIME_OUT(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Time Out", "Time Out"),
    ERROR_SAVING(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when saving data", "Gagal pada saat simpan data"),
    ERROR_UPDATING(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when update data", "Gagal pada saat perbaharui data"),
    ERROR_VALIDATE_OTP(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when validate otp", "Gagal pada saat validasi otp"),
    ERROR_UPLOAD_FILE(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when upload file", "Gagal pada saat unggah file"),
    ERROR_CHANGE_PASSWORD(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when change password", "Gagal ganti kata sandi"),
    ERROR_SEND_OTP(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when send otp", "Gagal pada saat kirim otp"),
    ERROR_DELETING(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when delete data", "Gagal pada saat menghapus data"),
    ERROR_FIND_DATA(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Error when get data", "Gagal pada saat mengambil data"),
    INTERNAL_SERVER_ERROR(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Internal Server Error", "Kesalahan server internal"),
    UNKNOWN_ERROR(ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR, "Unknown Error", "Error tidak diketahui");

    private int messageCode;
    private ResponseStatus responseStatus;
    private String descriptionEn;
    private String descriptionId;
}
