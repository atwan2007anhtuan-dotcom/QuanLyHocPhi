
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// Lop ngoai le tu dinh nghia
class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

// Lop truu tuong SinhVien
abstract class SinhVien {
    protected String maSV;
    protected int soTinChi;
    protected String maHe;

    public SinhVien(String maSV, int soTinChi, String maHe) {
        this.maSV = maSV;
        this.soTinChi = soTinChi;
        this.maHe = maHe;
    }

    public abstract long hocPhi();

    public String getMaSV() {
        return maSV;
    }

    public String getMaHe() {
        return maHe;
    }
}

// Sinh vien chinh quy
class ChinhQuy extends SinhVien {
    public ChinhQuy(String maSV, int soTinChi) {
        super(maSV, soTinChi, "CQ");
    }

    @Override
    public long hocPhi() {
        return soTinChi * 450000L;
    }
}

// Sinh vien chat luong cao
class ChatLuongCao extends SinhVien {
    public ChatLuongCao(String maSV, int soTinChi) {
        super(maSV, soTinChi, "CLC");
    }

    @Override
    public long hocPhi() {
        return (long)(soTinChi * 450000L * 1.5);
    }
}

// Sinh vien lien thong
class LienThong extends SinhVien {
    public LienThong(String maSV, int soTinChi) {
        super(maSV, soTinChi, "LT");
    }

    @Override
    public long hocPhi() {
        return soTinChi * 450000L + 2000000L;
    }
}

// Lop quan ly hoc phi
class QuanLyHocPhi {
    private ArrayList<SinhVien> danhSach = new ArrayList<>();
    private Set<String> tapMaSV = new HashSet<>();

    // Them sinh vien va kiem tra du lieu
    public void themSinhVien(String maHe, String maSV, String soTC)
            throws DuLieuKhongHopLeException {

        // Kiem tra ma he
        if (!maHe.equals("CQ") &&
            !maHe.equals("CLC") &&
            !maHe.equals("LT")) {
            throw new DuLieuKhongHopLeException("Ma he khong hop le");
        }

        // Kiem tra so tin chi co phai so nguyen khong
        int soTinChi;

        try {
            soTinChi = Integer.parseInt(soTC);
        } catch (NumberFormatException e) {
            throw new DuLieuKhongHopLeException("So tin chi khong hop le");
        }

        // Kiem tra gioi han tin chi
        if (soTinChi < 1 || soTinChi > 30) {
            throw new DuLieuKhongHopLeException("So tin chi ngoai gioi han");
        }

        // Kiem tra ma sinh vien trung
        if (tapMaSV.contains(maSV)) {
            throw new DuLieuKhongHopLeException("Ma sinh vien bi trung");
        }

        // Tao doi tuong theo ma he
        SinhVien sv;

        if (maHe.equals("CQ")) {
            sv = new ChinhQuy(maSV, soTinChi);
        } else if (maHe.equals("CLC")) {
            sv = new ChatLuongCao(maSV, soTinChi);
        } else {
            sv = new LienThong(maSV, soTinChi);
        }

        danhSach.add(sv);
        tapMaSV.add(maSV);
    }

    // Sap xep hoc phi giam dan, ma sinh vien tang dan
    public void sapXep() {
        Collections.sort(danhSach, new Comparator<SinhVien>() {
            @Override
            public int compare(SinhVien a, SinhVien b) {
                int kq = Long.compare(b.hocPhi(), a.hocPhi());

                if (kq == 0) {
                    return a.getMaSV().compareTo(b.getMaSV());
                }

                return kq;
            }
        });
    }

    // In danh sach sinh vien
    public void hienThi() {
        long tong = 0;

        for (SinhVien sv : danhSach) {
            System.out.println(
                sv.getMaSV() + " " +
                sv.getMaHe() + " " +
                sv.hocPhi()
            );

            tong += sv.hocPhi();
        }

        System.out.println("TONG " + tong);
    }
}

// Lop Main
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        QuanLyHocPhi quanLy = new QuanLyHocPhi();

        ArrayList<String> loi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] data = sc.nextLine().trim().split("\\s+");

            String maHe = data[0];
            String maSV = data[1];
            String soTC = data[2];

            try {
                quanLy.themSinhVien(maHe, maSV, soTC);
            } catch (DuLieuKhongHopLeException e) {
                loi.add("INVALID " + maSV);
            }
        }

        // In cac dong khong hop le truoc
        for (String s : loi) {
            System.out.println(s);
        }

        // Sap xep va in danh sach hop le
        quanLy.sapXep();
        quanLy.hienThi();

        sc.close();
    }
}
