package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.DaftarModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

@Mapper
public interface SidukMapper {
	@Select("select nik, nama, tempat_lahir, tanggal_lahir, golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, status_dalam_keluarga, id_keluarga "
			+ "from penduduk where nik = #{nik}")
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select Kg.nomor_kk, Kg.alamat, Kg.RT, Kg.RW, Kl.nama_kelurahan, Kc.nama_kecamatan, Kt.nama_kota "
			+ "from penduduk P join keluarga Kg on P.id_keluarga = Kg.id "
			+ "join kelurahan Kl on Kg.id_kelurahan = Kl.id "
			+ "join kecamatan Kc on Kl.id_kecamatan = Kc.id "
			+ "join kota Kt on Kc.id_kota = Kt.id "
			+ "where P.nik= #{nik}")
	KeluargaModel selectKeluargaPenduduk (@Param("nik") String nik);
	
	@Select("select nomor_kk "
			+ "from keluarga "
			+ "where id = #{id_keluarga}")
	String cariNKKpenduduk(@Param("id_keluarga") String id_keluarga);
	
	@Select("select nik "
			+ "from penduduk "
			+ "where nik like concat(#{nik},'%') "
			+ "order by nik desc "
			+ "limit 1")
	PendudukModel cariNIK(@Param("nik") String nik);
	
	@Select("select id "
			+ "from penduduk "
			+ "order by id desc "
			+ "limit 1")
	PendudukModel id();
	
	@Insert("insert into penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) "
			+ "values (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat}) ")
	void addPenduduk(@Param("id") String id,
			@Param("nik") String nik,
			@Param("nama") String nama,
			@Param("tempat_lahir") String tempat_lahir,
			@Param("tanggal_lahir") String tanggal_lahir,
			@Param("jenis_kelamin") int jenis_kelamin,
			@Param("is_wni") int is_wni,
			@Param("id_keluarga") String id_keluarga,
			@Param("agama") String agama,
			@Param("pekerjaan") String pekerjaan,
			@Param("status_perkawinan") String status_perkawinan,
			@Param("status_dalam_keluarga") String status_dalam_keluarga,
			@Param("golongan_darah") String golongan_darah,
			@Param("is_wafat") int is_wafat);
	
	@Update("update penduduk "
			+ "set nik = #{nikBaru}, "
			+ "nama = #{nama}, "
			+ "tempat_lahir = #{tempat_lahir}, "
			+ "tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, "
			+ "is_wni = #{is_wni}, "
			+ "id_keluarga = #{id_keluarga}, "
			+ "agama = #{agama}, "
			+ "pekerjaan = #{pekerjaan}, "
			+ "status_perkawinan = #{status_perkawinan}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga}, "
			+ "golongan_darah = #{golongan_darah}, "
			+ "is_wafat = #{is_wafat} "
			+ "where nik = #{nikLama}")
	void ubahPendudukNIK(@Param("nikLama") String nikLama,
			@Param("nikBaru") String nikBaru,
			@Param("nama") String nama,
			@Param("tempat_lahir") String tempat_lahir,
			@Param("tanggal_lahir") String tanggal_lahir,
			@Param("jenis_kelamin") int jenis_kelamin,
			@Param("is_wni") int is_wni,
			@Param("id_keluarga") String id_keluarga,
			@Param("agama") String agama,
			@Param("pekerjaan") String pekerjaan,
			@Param("status_perkawinan") String status_perkawinan,
			@Param("status_dalam_keluarga") String status_dalam_keluarga,
			@Param("golongan_darah") String golongan_darah,
			@Param("is_wafat") int is_wafat);
	
	@Update("update penduduk "
			+ "set nama = #{nama}, "
			+ "tempat_lahir = #{tempat_lahir}, "
			+ "tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, "
			+ "is_wni = #{is_wni}, "
			+ "id_keluarga = #{id_keluarga}, "
			+ "agama = #{agama}, "
			+ "pekerjaan = #{pekerjaan}, "
			+ "status_perkawinan = #{status_perkawinan}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga}, "
			+ "golongan_darah = #{golongan_darah}, "
			+ "is_wafat = #{is_wafat} "
			+ "where nik = #{nikLama}")
	void ubahPenduduk(@Param("nikLama") String nikLama,
			@Param("nama") String nama,
			@Param("tempat_lahir") String tempat_lahir,
			@Param("tanggal_lahir") String tanggal_lahir,
			@Param("jenis_kelamin") int jenis_kelamin,
			@Param("is_wni") int is_wni,
			@Param("id_keluarga") String id_keluarga,
			@Param("agama") String agama,
			@Param("pekerjaan") String pekerjaan,
			@Param("status_perkawinan") String status_perkawinan,
			@Param("status_dalam_keluarga") String status_dalam_keluarga,
			@Param("golongan_darah") String golongan_darah,
			@Param("is_wafat") int is_wafat);
	
	@Select("select Kg.nomor_kk, Kg.alamat, Kg.RT, Kg.RW, Kl.nama_kelurahan, Kc.nama_kecamatan, Kt.nama_kota "
			+ "from keluarga Kg join kelurahan Kl on Kg.id_kelurahan = Kl.id "
			+ "join kecamatan Kc on Kl.id_kecamatan = Kc.id "
			+ "join kota Kt on Kc.id_kota = Kt.id "
			+ "where nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"),
			@Result(property="nama_kota", column="nama_kota"),
			@Result(property="pendudukKeluarga", column="nomor_kk",
					javaType = List.class,
					many=@Many(select="selectPendudukKeluarga"))
	})
	KeluargaModel selectKeluarga (@Param("nkk") String nkk);
	
	@Select("select penduduk.nama, nik, jenis_kelamin, tempat_lahir, tanggal_lahir, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, is_wni, is_wafat "
			+ "from penduduk join keluarga "
			+ "on keluarga.id = penduduk.id_keluarga "
			+ "where keluarga.nomor_kk = #{nkk}")
	List<PendudukModel> selectPendudukKeluarga (@Param("nkk") String nkk);
	
	
	@Select("select kode_kecamatan "
			+ "from kecamatan "
			+ "where nama_kecamatan = #{nama_kecamatan}")
	String cariKodeKecamatan (@Param("nama_kecamatan") String nama_kecamatan);
	
	@Select("select nomor_kk "
			+ "from keluarga "
			+ "where nomor_kk like concat(#{nkk},'%') "
			+ "order by nomor_kk desc "
			+ "limit 1")
	KeluargaModel cariNKK(@Param("nkk") String nkk);
	
	@Select("select id "
			+ "from kelurahan "
			+ "where nama_kelurahan = #{nama_kelurahan}")
	String cariIDkelurahan (@Param("nama_kelurahan") String nama_kelurahan);
	
	@Select("select id "
			+ "from keluarga "
			+ "order by id desc "
			+ "limit 1")
	KeluargaModel idKeluarga();
	
	@Insert("insert into keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) "
			+ "values (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void addKeluarga(@Param("id") String id,
			@Param("nomor_kk") String nomor_kk,
			@Param("alamat") String alamat,
			@Param("rt") String rt,
			@Param("rw") String rw,
			@Param("id_kelurahan") String id_kelurahan,
			@Param("is_tidak_berlaku") int is_tidak_berlaku);
	
	@Update("update keluarga "
			+ "set nomor_kk = #{nkkBaru}, "
			+ "alamat = #{alamat}, "
			+ "rt = #{rt}, "
			+ "rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan} "
			+ "where nomor_kk = #{nkkLama}")
	void ubahKeluarga(@Param("nkkLama") String nkkLama,
			@Param("nkkBaru") String nkkBaru,
			@Param("alamat") String alamat,
			@Param("rt") String rt,
			@Param("rw") String rw,
			@Param("id_kelurahan") String id_kelurahan);
	
	
	@Update("update penduduk set is_wafat=1 where nik = #{nik}")
	void updateWafat (@Param("nik") String nik);
	
	@Update("update keluarga set is_tidak_berlaku=1 where nomor_kk = #{nkk}")
	void updateStatusKeluarga (@Param("nkk") String nkk);
	
	@Select("select * from kota")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kode_kota", column="kode_kota"),
			@Result(property="nama_kota", column="nama_kota")
	})
	List<KotaModel> daftarKota();
	
	@Select("select * from kota where id=#{id_kota}")
	KotaModel selectKota(@Param("id_kota") String id_kota);
	
	@Select("select * from kecamatan where id_kota = #{id_kota}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="id_kota", column="id_kota"),
			@Result(property="kode_kecamatan", column="kode_kecamatan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan")
	})
	List<KecamatanModel> daftarKecamatan(@Param("id_kota") String id_kota);
	
	@Select("select * from kecamatan where id=#{id_kecamatan}")
	KecamatanModel selectKecamatan(@Param("id_kecamatan") String id_kecamatan);
	
	@Select("select * from kelurahan where id_kecamatan = #{id_kecamatan}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="id_kecamatan", column="id_kecamatan"),
			@Result(property="kode_kelurahan", column="kode_kelurahan"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="kode_pos", column="kode_pos")
	})
	List<KelurahanModel> daftarKelurahan(@Param("id_kecamatan") String id_kecamatan);
	
	@Select("select * from kelurahan where id=#{id_kelurahan}")
	KelurahanModel selectKelurahan(@Param("id_kelurahan") String id_kelurahan);
	
	@Select("select P.nik, P.nama, P.jenis_kelamin "
			+ "from penduduk P join keluarga Kg on P.id_keluarga = Kg.id "
			+ "where Kg.id_kelurahan = #{id_kelurahan}")
	@Results(value = {
			@Result(property="nik", column="nik"),
			@Result(property="nama", column="nama"),
			@Result(property="jenis_kelamin", column="jenis_kelamin")
	})
	List<PendudukModel> daftarPenduduk(@Param("id_kelurahan") String id_kelurahan);
}
