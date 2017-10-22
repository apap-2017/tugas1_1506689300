package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.SidukMapper;
import com.example.model.PendudukModel;
import com.example.model.DaftarModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SidukServiceDatabase implements SidukService
{
    @Autowired
    private SidukMapper sidukMapper;
    
    @Override
    public PendudukModel selectPenduduk (String nik)
    {
        log.info ("select penduduk with nik {}", nik);
        return sidukMapper.selectPenduduk(nik);
    }
    
    @Override
    public KeluargaModel selectKeluargaPenduduk (String nik)
    {
        log.info ("select keluarga penduduk with nik {}", nik);
        return sidukMapper.selectKeluargaPenduduk(nik);
    }
    
    @Override
    public String buatNIK(String id_keluarga, String tanggal_lahir, int jenis_kelamin)
    {
    	String nik = "";
    	
    	String nkk = sidukMapper.cariNKKpenduduk(id_keluarga);
    	nik += nkk.substring(0, 6);
    	
    	long tanggal = Long.parseLong(tanggal_lahir.substring(8, 10));
    	
    	if(jenis_kelamin == 1) {
    		tanggal = Long.parseLong(tanggal_lahir.substring(8, 10))+40;
    	}
    	
    	String tanggalS = "" + tanggal;
    	nik += tanggalS + tanggal_lahir.substring(5, 7) + tanggal_lahir.substring(2, 4) ;
    	
    	PendudukModel cariNIK = sidukMapper.cariNIK(nik);
    	if(cariNIK == null) {
    		nik += "0001";
    	} else {
    		long doubleNIK = Long.parseLong(cariNIK.getNik())+1;
    		nik = "" + doubleNIK;
    	}
    	
    	log.info("nik final " + nik);
    	return nik;
    }
    
    @Override
    public String id()
    {
    	PendudukModel penduduk = sidukMapper.id();
    	long idP = Long.parseLong(penduduk.getId())+1;
		String id = "" + idP;
    	return id;
    }
    
    @Override
    public void addPenduduk(String id, String nik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, int is_wni, String id_keluarga,
			String agama, String pekerjaan, String status_perkawinan, String status_dalam_keluarga, String golongan_darah, int is_wafat) 
    {
    	sidukMapper.addPenduduk(id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga,
				agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
    }
    
    @Override
    public void ubahPendudukNIK(String nikLama, String nikBaru, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, int is_wni, String id_keluarga,
			String agama, String pekerjaan, String status_perkawinan, String status_dalam_keluarga, String golongan_darah, int is_wafat) 
    {
    	sidukMapper.ubahPendudukNIK(nikLama, nikBaru, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga,
				agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
    }
    
    @Override
    public void ubahPenduduk(String nikLama, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, int is_wni, String id_keluarga,
			String agama, String pekerjaan, String status_perkawinan, String status_dalam_keluarga, String golongan_darah, int is_wafat) 
    {
    	sidukMapper.ubahPenduduk(nikLama, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga,
				agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
    }
    
    @Override
    public KeluargaModel selectKeluarga (String nkk) 
    {
    	log.info ("select keluarga with nkk {}", nkk);
		return sidukMapper.selectKeluarga(nkk);
    }
    
    @Override
    public List<PendudukModel> selectPendudukKeluarga(String nkk)
    {
    	log.info("select anggota keluarga " + nkk);
    	return sidukMapper.selectPendudukKeluarga(nkk);
    }
    
    @Override
    public String buatNKK(String nama_kecamatan)
    {
    	String nkk = "";
    	
    	log.info("cari kode kecamatan " + nama_kecamatan);
    	String kodeKecamatan = sidukMapper.cariKodeKecamatan(nama_kecamatan).substring(0,6);
    	nkk += kodeKecamatan;
    	
    	LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String tanggal = date.format(formatter);
        nkk += tanggal;
        
    	KeluargaModel cariNKK = sidukMapper.cariNKK(nkk);
    	if(cariNKK == null) {
    		nkk += "0001";
    	} else {
    		long doubleNKK = Long.parseLong(cariNKK.getNomor_kk())+1;
    		nkk = "" + doubleNKK;
    	}
    	
    	log.info("berhasil buat " + nkk);
    	return nkk;
    }
    
    @Override
    public String cariIDkelurahan(String nama_kelurahan) 
    {
    	log.info("cari id kelurahan " + nama_kelurahan);
    	String id_kelurahan = sidukMapper.cariIDkelurahan(nama_kelurahan);
    	return id_kelurahan;
    }
    
    @Override
    public String idKeluarga()
    {
    	KeluargaModel keluarga = sidukMapper.idKeluarga();
    	long id = Long.parseLong(keluarga.getId())+1;
		String idKeluarga = "" + id;
    	return idKeluarga;
    }
    
    @Override
    public void addKeluarga(String id, String nomor_kk, String alamat, String rt, String rw, String id_kelurahan, int is_tidak_berlaku)
    {
    	log.info("tambah keluarga");
    	sidukMapper.addKeluarga(id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku);
    }
    
    @Override
    public void ubahKeluarga(String nkkLama, String nkkBaru, String alamat, String rt, String rw, String id_kelurahan)
    {
    	log.info("ubah keluarga");
    	sidukMapper.ubahKeluarga(nkkLama, nkkBaru, alamat, rt, rw, id_kelurahan);
    }
    
    @Override
    public void updateWafat (String nik) {
    	log.info("penduduk " + nik + " update wafat");
    	sidukMapper.updateWafat(nik);
    	
    }
    
    @Override
    public void updateStatusKeluarga (String nkk) {
    	log.info("keluarga " + nkk + " update status");
    	sidukMapper.updateStatusKeluarga(nkk);
    }
    
    @Override
    public List<KotaModel> daftarKota(){
    	return sidukMapper.daftarKota();
    }
    
    @Override
    public KotaModel selectKota(String id_kota) {
    	return sidukMapper.selectKota(id_kota);
    }
    
    @Override
    public List<KecamatanModel> daftarKecamatan(String id_kota){
    	return sidukMapper.daftarKecamatan(id_kota);
    }
    
    @Override
    public KecamatanModel selectKecamatan(String id_kecamatan) {
    	return sidukMapper.selectKecamatan(id_kecamatan);
    }
    
    @Override
    public List<KelurahanModel> daftarKelurahan(String id_kecamatan){
    	return sidukMapper.daftarKelurahan(id_kecamatan);
    }
    
    @Override
    public KelurahanModel selectKelurahan(String id_kelurahan) {
    	return sidukMapper.selectKelurahan(id_kelurahan);
    }
    
    @Override
    public List<PendudukModel> daftarPenduduk(String id_kelurahan){
    	return sidukMapper.daftarPenduduk(id_kelurahan);
    }
}
