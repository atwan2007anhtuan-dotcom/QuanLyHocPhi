
import java.util.Scanner;

// Lop truu tuong
abstract class SinhVien {
    protected String maSV;
    protected int soTinChi;

    public SinhVien(String maSV, int soTinChi) {
        this.maSV = maSV;
        this.soTinChi = soTinChi;
    }

    public abstract long hocPhi();

    public String getMaSV() {
        return maSV;
    }
}

// Lop sinh vien chinh quy
class ChinhQuy extends SinhVien {

    public ChinhQuy(String maSV, int soTinChi) {
        super(maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * 450000L;
    }
}

// Lop sinh vien chat luong cao
class ChatLuongCao extends SinhVien {

    public ChatLuongCao(String maSV, int soTinChi) {
        super(maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return (long)(soTinChi * 450000L * 1.5);
    }
}

// Lop sinh vien lien thong
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

        SinhVien[] danhSach = new SinhVien[n];

        for (int i = 0; i < n; i++) {
            String[] data = sc.nextLine().trim().split("\\s+");

            String maHe = data[0];
            String maSV = data[1];
            int soTinChi = Integer.parseInt(data[2]);

            if (maHe.equals("CQ")) {
                danhSach[i] = new ChinhQuy(maSV, soTinChi);
            } else if (maHe.equals("CLC")) {
                danhSach[i] = new ChatLuongCao(maSV, soTinChi);
            } else if (maHe.equals("LT")) {
                danhSach[i] = new LienThong(maSV, soTinChi);
            }
        }

        for (SinhVien sv : danhSach) {
            System.out.println(
                sv.getMaSV() + " " + 
                (sv instanceof ChinhQuy ? "CQ" :
                 sv instanceof ChatLuongCao ? "CLC" : "LT") + " " +
                sv.hocPhi()
            );
        }

        sc.close();
    }
}
