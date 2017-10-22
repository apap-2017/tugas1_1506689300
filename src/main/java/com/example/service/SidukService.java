package com.example.service;

import java.util.List;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

public interface SidukService {
	PendudukModel selectPenduduk (String nik);
	KeluargaModel selectKeluargaPenduduk (String nik);
	String buatNIK(String id_keluarga, String tanggal_lahir, int jenis_kelamin);
	String id();
	void addPenduduk(String id, String nik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, int is_wni, String id_keluarga,
			String agama, String pekerjaan, String status_perkawinan, String status_dalam_keluarga, String golongan_darah, int is_wafat); 
	
	void ubahPendudukNIK(String nikLama, String nikBaru, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, int is_wni, String id_keluarga,
			String agama, String pekerjaan, String status_perkawinan, String status_dalam_keluarga, String golongan_darah, int is_wafat);
	void ubahPenduduk(String nikLama, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, int is_wni, String id_keluarga,
			String agama, String pekerjaan, String status_perkawinan, String status_dalam_keluarga, String golongan_darah, int is_wafat);
	
	KeluargaModel selectKeluarga (String nkk);
	List<PendudukModel> selectPendudukKeluarga(String nkk);
	
	String buatNKK(String nama_kecamatan);
	String cariIDkelurahan(String nama_kelurahan);
	String idKeluarga();
	void addKeluarga(String id, String nomor_kk, String alamat, String rt, String rw, String id_kelurahan, int is_tidak_berlaku);
	void ubahKeluarga(String nkkLama, String nkkBaru, String alamat, String rt, String rw, String id_kelurahan);
	
	void updateWafat(String nik);
	void updateStatusKeluarga (String nkk);
	
	List<String> cariKota();
}
