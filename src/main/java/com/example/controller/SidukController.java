package com.example.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.PendudukModel;
import com.example.model.KeluargaModel;
import com.example.service.SidukService;



import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Controller
public class SidukController {
	@Autowired
	SidukService sidukDAO;
	
	@RequestMapping("/")
    public String index ()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk")
	public String viewPenduduk (Model model,
							@RequestParam(value = "nik", required = true) String nik)
	{
		PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
		KeluargaModel keluarga = sidukDAO.selectKeluargaPenduduk(nik);
		
		if(penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			return "view-penduduk";
		} else {
			return "";
		}
	}
	
	@RequestMapping(value = "/penduduk/tambah")
	public String pendudukTambahForm()
	{
		return "add-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String pendudukTambah(Model model,
			@RequestParam(value = "nama") String nama,
			@RequestParam(value = "tempat_lahir") String tempat_lahir,
			@RequestParam(value = "tanggal_lahir") String tanggal_lahir,
			@RequestParam(value = "jenis_kelamin") int jenis_kelamin,
			@RequestParam(value = "golongan_darah") String golongan_darah,
			@RequestParam(value = "agama") String agama,
			@RequestParam(value = "status_perkawinan") String status_perkawinan,
			@RequestParam(value = "pekerjaan") String pekerjaan,
			@RequestParam(value = "status_dalam_keluarga") String status_dalam_keluarga,
			@RequestParam(value = "is_wni") int is_wni,
			@RequestParam(value = "is_wafat") int is_wafat,
			@RequestParam(value = "id_keluarga") String id_keluarga)
	{
		String nik = sidukDAO.buatNIK(id_keluarga, tanggal_lahir, jenis_kelamin);
		String id = sidukDAO.id();
		
		sidukDAO.addPenduduk(id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga,
				agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
		
		model.addAttribute("nik", nik);
		
		return "sukses-add-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}")
	public String pendudukUbahForm(Model model, @PathVariable(value = "nik") String nik)
	{
		PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
		
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("nik", nik);
		
		return "ubah-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String pendudukUbah(Model model,
			@RequestParam(value = "nama") String nama,
			@RequestParam(value = "tempat_lahir") String tempat_lahir,
			@RequestParam(value = "tanggal_lahir") String tanggal_lahir,
			@RequestParam(value = "jenis_kelamin") int jenis_kelamin,
			@RequestParam(value = "golongan_darah") String golongan_darah,
			@RequestParam(value = "agama") String agama,
			@RequestParam(value = "status_perkawinan") String status_perkawinan,
			@RequestParam(value = "pekerjaan") String pekerjaan,
			@RequestParam(value = "status_dalam_keluarga") String status_dalam_keluarga,
			@RequestParam(value = "is_wni") int is_wni,
			@RequestParam(value = "is_wafat") int is_wafat,
			@RequestParam(value = "id_keluarga") String id_keluarga,
			@RequestParam(value = "nik") String nikLama)
	{
		PendudukModel penduduk = sidukDAO.selectPenduduk(nikLama);
		
		if(!penduduk.getTanggal_lahir().equals(tanggal_lahir) || !penduduk.getId_keluarga().equals(id_keluarga) || (penduduk.getJenis_kelamin() != jenis_kelamin)) {
			String nikBaru = sidukDAO.buatNIK(id_keluarga, tanggal_lahir, jenis_kelamin);
			sidukDAO.ubahPendudukNIK(nikLama, nikBaru, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga,
					agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
		} else {
			sidukDAO.ubahPenduduk(nikLama, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga,
					agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
		}
		
		model.addAttribute("nik", nikLama);
		
		return "sukses-ubah-penduduk";
	}
	
	
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public String pendudukWafat(Model model,
			@RequestParam(value = "nik") String nik,
			@RequestParam(value = "nkk") String nkk)
	{
		sidukDAO.updateWafat(nik);
		
		List<PendudukModel> pendudukKeluarga = sidukDAO.selectPendudukKeluarga(nkk);
		boolean gantiStatus = true;
		for(int i = 0; i < pendudukKeluarga.size(); i++) {
			if(pendudukKeluarga.get(i).getIs_wafat() == 0) {
				gantiStatus = false;
			}
		}
		if(gantiStatus == true) {
			sidukDAO.updateStatusKeluarga(nkk);
			log.info("ganti status keluarga " + nkk);
		}
		
		model.addAttribute("nik", nik);
		return "sukses-update-wafat";
	}
	
	@RequestMapping("/keluarga")
	public String viewKeluarga(Model model, @RequestParam(value = "nkk", required = true) String nkk)
	{
		KeluargaModel keluarga = sidukDAO.selectKeluarga(nkk);
		
		if(keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "view-keluarga";
		} else {
			return "";
		}
	}
	
	@RequestMapping(value = "/keluarga/tambah")
	public String keluargaTambahForm()
	{
		return "add-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String keluargaTambah(Model model,
			@RequestParam(value = "alamat") String alamat,
			@RequestParam(value = "rt") String rt,
			@RequestParam(value = "rw") String rw,
			@RequestParam(value = "nama_kelurahan") String nama_kelurahan,
			@RequestParam(value = "nama_kecamatan") String nama_kecamatan,
			@RequestParam(value = "nama_kota") String nama_kota)
	{
		String nomor_kk = sidukDAO.buatNKK(nama_kecamatan);
		String id_kelurahan = sidukDAO.cariIDkelurahan(nama_kelurahan);
		String id_keluarga = sidukDAO.idKeluarga();
		
		sidukDAO.addKeluarga(id_keluarga, nomor_kk, alamat, rt, rw, id_kelurahan, 0);
		
		model.addAttribute("nomor_kk", nomor_kk);
		return "sukses-add-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}")
	public String keluargaUbahForm(Model model, @PathVariable(value = "nkk") String nkk)
	{
		KeluargaModel keluarga = sidukDAO.selectKeluarga(nkk);
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("nkk", nkk);
		
		return "ubah-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String keluargaUbah(Model model,
			@RequestParam(value = "alamat") String alamat,
			@RequestParam(value = "rt") String rt,
			@RequestParam(value = "rw") String rw,
			@RequestParam(value = "nama_kelurahan") String nama_kelurahan,
			@RequestParam(value = "nama_kecamatan") String nama_kecamatan,
			@RequestParam(value = "nama_kota") String nama_kota,
			@RequestParam(value = "nkk") String nkkLama)
	{
		String nkkBaru = sidukDAO.buatNKK(nama_kecamatan);
		String id_kelurahan = sidukDAO.cariIDkelurahan(nama_kelurahan);
		
		sidukDAO.ubahKeluarga(nkkLama, nkkBaru, alamat, rt, rw, id_kelurahan);
		
		model.addAttribute("nomor_kk", nkkLama);
		return "sukses-ubah-keluarga";
	}
	
	
//	@RequestMapping("/penduduk/cari")
//	public String cariKota(Model model, List list)
//	{
//		List<String> daftarKota = sidukDAO.cariKota();
//		list.addAtrribute(daftarKota);
//		return "cari-kota";
//	}
}
