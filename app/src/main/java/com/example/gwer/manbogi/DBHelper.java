package com.example.gwer.manbogi;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DBHelper extends SQLiteOpenHelper {

        // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        // DB생성시 호출되는 함수
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 새로운 테이블 생성
        /* 이름은 MANBORECORD이고,
        days 문자열 컬럼, step 정수형 컬럼, coin 정수형 컬럼으로 구성된 테이블을 생성. */
            db.execSQL("CREATE TABLE MANBORECORD (days TEXT PRIMARY KEY, step INTEGER, coin INTEGER);");

        }

        // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void insert(String days, int step, int coin) {
            // 읽고 쓰기가 가능하게 DB 열기
            SQLiteDatabase db = getWritableDatabase();
            // DB에 입력한 값으로 행 추가
            db.execSQL("INSERT INTO MANBORECORD VALUES('" + days + "', " + step + ", " + coin + ") "+
                    "ON DUPLICATE KEY UPDATE coin = "+ coin +" , step =" +step+";");
            //db.execSQL("DROP TABLE MANBORECORD;");
            db.close();
        }

        public void update(String days, int step, int coin) {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행의 가격 정보 수정
            db.execSQL("UPDATE MANBORECORD SET coin=" + coin + ",step="+step+" WHERE days='" + days + "';");
            db.close();
        }

        public void delete(String days) {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행 삭제
            db.execSQL("DELETE FROM MANBORECORD WHERE days='" + days + "';");
            db.close();
        }

        public String getResult() {
            // 읽기가 가능하게 DB 열기
            SQLiteDatabase db = getReadableDatabase();
            String result = "";

            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            Cursor cursor = db.rawQuery("SELECT * FROM MANBORECORD", null);
            while (cursor.moveToNext()) {
                result += cursor.getString(0)
                        + " /걸음수 : "
                        + cursor.getInt(1)
                        + " /코인 : "
                        + cursor.getString(2)
                        + "원\n";
            }

            return result;
        }


}
