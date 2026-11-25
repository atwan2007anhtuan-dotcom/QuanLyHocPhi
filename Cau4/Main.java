
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Interface mien giam
interface MienGiam {
    long tinhGiam(long hocPhi);
}

// Chinh sach con chinh sach: giam 50%
class ConChinhSach implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 50 / 100;
    }
}

// Chinh sach ho ngheo: giam 70%
class HoNgheo implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 70 / 100;
    }
}

// Chinh sach hoc bong: giam toi da 3 trieu
class HocBong implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(hocPhi, 3000000L);
    }
}

// Lop truu tuong SinhVien
abstract class SinhVien {
    protected String maSV;
    protected int soTinChi;
    protected MienGiam mienGiam;

    public SinhVien(String maSV, int soTinChi) {
        this.maSV = maSV;
        this.soTinChi = soTinChi;
        this.mienGiam = null;
    }

    public abstract long hocPhi();

    public String getMaSV() {
        return maSV;
    }

    public void setMienGiam(MienGiam mienGiam) {
        this.mienGiam = mienGiam;
    }

    public long tienGiam() {
        if (mienGiam == null) {
            return 0;
        }

        return mienGiam.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - tienGiam();
    }
}

// Sinh vien chinh quy
class ChinhQuy extends SinhVien {
    public ChinhQuy(String maSV, int soTinChi) {
        super(maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * 450000L;
    }
}

// Sinh vien chat luong cao
class ChatLuongCao extends SinhVien {
    public ChatLuongCao(String maSV, int soTinChi) {
        super(maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return (long)(soTinChi * 450000L * 1.5);
    }
}

// Sinh vien lien thong
class LienThong extends SinhVien {
    public LienThong(String maSV, int soTinChi) {
        super(maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * 450000L + 2000000L;
    }
}

// Lop Main
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        ArrayList<SinhVien> danhSach = new ArrayList<>();

        // Luu sinh vien theo ma sinh vien
        Map<String, SinhVien> mapSV = new HashMap<>();

        // Doc danh sach sinh vien
        for (int i = 0; i < n; i++) {
            String[] data = sc.nextLine().trim().split("\\s+");

            String maHe = data[0];
            String maSV = data[1];
            int soTinChi = Integer.parseInt(data[2]);

            SinhVien sv;

            if (maHe.equals("CQ")) {
                sv = new ChinhQuy(maSV, soTinChi);
            } else if (maHe.equals("CLC")) {
                sv = new ChatLuongCao(maSV, soTinChi);
            } else {
                sv = new LienThong(maSV, soTinChi);
            }

            danhSach.add(sv);
            mapSV.put(maSV, sv);
        }

        // Doc danh sach mien giam
        int m = Integer.parseInt(sc.nextLine());

        ArrayList<String> loi = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            String[] data = sc.nextLine().trim().split("\\s+");

            String maSV = data[0];
            String maChinhSach = data[1];

            // Tim sinh vien
            SinhVien sv = mapSV.get(maSV);

            if (sv == null) {
                loi.add("NOT FOUND " + maSV);
                continue;
            }

            // Gan chinh sach mien giam
            if (maChinhSach.equals("CS")) {
                sv.setMienGiam(new ConChinhSach());
            } else if (maChinhSach.equals("HN")) {
                sv.setMienGiam(new HoNgheo());
            } else if (maChinhSach.equals("HB")) {
                sv.setMienGiam(new HocBong());
            }
        }

        // In cac ma sinh vien khong ton tai
        for (String s : loi) {
            System.out.println(s);
        }

        // Thong ke theo he dao tao
        Map<String, Integer> soLuong = new HashMap<>();
        Map<String, Long> tongTien = new HashMap<>();

        // In danh sach sinh vien theo thu tu nhap
        for (SinhVien sv : danhSach) {
            String maHe;

            if (sv instanceof ChinhQuy) {
                maHe = "CQ";
            } else if (sv instanceof ChatLuongCao) {
                maHe = "CLC";
            } else {
                maHe = "LT";
            }

            long hocPhi = sv.hocPhi();
            long giam = sv.tienGiam();
            long phaiNop = sv.phaiNop();

            System.out.println(
                sv.getMaSV() + " " +
                maHe + " " +
                hocPhi + " " +
                giam + " " +
                phaiNop
            );

            soLuong.put(
                maHe,
                soLuong.getOrDefault(maHe, 0) + 1
            );

            tongTien.put(
                maHe,
                tongTien.getOrDefault(maHe, 0L) + phaiNop
            );
        }

        // Thong ke theo thu tu CQ, CLC, LT
        String[] cacHe = {"CQ", "CLC", "LT"};

        for (String maHe : cacHe) {
            if (soLuong.containsKey(maHe)) {
                System.out.println(
                    maHe + " " +
                    soLuong.get(maHe) + " " +
                    tongTien.get(maHe)
                );
            }
        }

        sc.close();
    }
}
