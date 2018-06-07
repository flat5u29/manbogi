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
        db.execSQL("CREATE TABLE MANBORECORD (_id INTEGER PRIMARY KEY AUTOINCREMENT, days TEXT , step INTEGER, coin INTEGER);");

        }

        // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void insert(String days, int step, int coin) {
            // 읽고 쓰기가 가능하게 DB 열기
            SQLiteDatabase db = getWritableDatabase();
            // DB에 입력한 값으로 행 추가
            db.execSQL("INSERT INTO MANBORECORD VALUES(null, '" + days + "', " + step + ", " + coin + ");");
            db.close();
        }

        public void update(String days, int step, int coin) {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행의 가격 정보 수정
            db.execSQL("UPDATE MANBORECORD SET coin=" + coin + ",step="+step+" WHERE days='" + days + "';");
            db.close();
        }

        public void delete() {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행 삭제
            db.execSQL("DELETE FROM MANBORECORD");
            // 삭제할때 _id 초기화
            db.execSQL("UPDATE SQLITE_SEQUENCE SET seq = 1 WHERE name = 'MANBORECORD'; ");
            db.close();
        }

        // 데이터베이스에서 날짜정보를 검색하는 메소드
        // 메인에서 오늘날짜의 데이터 유무검색에 사용
        public String select(String days) {
            SQLiteDatabase db = getReadableDatabase();
            String result = "";

            Cursor cursor = db.rawQuery("SELECT * FROM MANBORECORD WHERE days='" + days + "';",null);

            while (cursor.moveToNext()) {

                result += cursor.getString(1);

            }

            return result;
        }


        // 데이터베이스에서 마지막 아이디(_id의 최대값) 코인의 정보를 검색하는 메소드
        public int selectcoin() {
            SQLiteDatabase db = getReadableDatabase();
            int result = 0;

            // 날짜를 받아서 select문으로 검색하여 정보추출
            Cursor cursor = db.rawQuery("SELECT coin FROM MANBORECORD ORDER BY _id desc limit 1",null);

                // 코인의 정보
            if( cursor != null && cursor.moveToFirst() ){
                result = cursor.getInt(cursor.getColumnIndex("coin"));
                cursor.close();
            }


            return result;
        }

        // 데이터베이스에서 걸음수의 정보를 검색하는 메소드
        public int stepCount(String days) {
            SQLiteDatabase db = getReadableDatabase();
            int result = 0;

            Cursor cursor = db.rawQuery("SELECT * FROM MANBORECORD WHERE days='" + days + "';",null);

            while (cursor.moveToNext()) {

                // 데이터에서 컬럼인덱스2번이 걸음수
                result += cursor.getInt(2);

            }

            return result;
        }

        public String getResult() {
            // 읽기가 가능하게 DB 열기
            SQLiteDatabase db = getReadableDatabase();
            String result = "";

            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            Cursor cursor = db.rawQuery("SELECT * FROM MANBORECORD", null);
            while (cursor.moveToNext()) {
                result += cursor.getString(0) // ID
                        + " : 날짜 "
                        + cursor.getString(1) // 날짜
                        + " /걸음수 : "
                        + cursor.getInt(2) // 걸음수
                        + " /코인 : "
                        + cursor.getInt(3) // 코인
                        + "원\n";
            }

            return result;
        }


}
