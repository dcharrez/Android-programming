# Retrofit 

### What is Retrofit?

* Retrofit is type-safe HTTP client for Android and Java by Square, Inc.
* It makes it relatively easy to retrieve and upload JSON via a REST based webservice.
* It automatically serializes the JSON response using a POJO which must be defined in advanced for the JSON Structure.


### Basics of retrofit just in 8 steps

1. Add gradle dependencies
```
    dependencies {
    
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation 'com.android.support.constraint:constraint-layout:1.0.2'

    implementation 'com.android.support:design:26.1.0'
    compile "com.android.support:recyclerview-v7:26.1.0"
    compile "com.android.support:cardview-v7:26.1.0"

    compile 'com.squareup.retrofit2:retrofit:2.3.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'

}
```
2. Add uses-permission to Manifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.mobotechnology.bipinpandey.retrofit_handdirty">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        ......
        ......
    </application>

</manifest>
```
3. Creating Layout files

Review activity_mail.xml, content_main.xml and single_row_view.xml.

4. Creating POJO (Plain Old Java Object)

Review Notice.java and NoticeList.java

5. Setting Up the Retrofit Interface
```
public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.myjson.com/";

    /**
     * Create an instance of Retrofit object
     * */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
```
6. Creating Layout files

Review GetNoticeDataService in my_intercade folder
```
public interface GetNoticeDataService 
{
    @GET("bins/1bsqcn/")
    Call<NoticeList> getNoticeData();
}
```
7. Add gradle dependencies
```
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private ArrayList<Notice> dataList;

    public NoticeAdapter(ArrayList<Notice> dataList) {
        this.dataList = dataList;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view_row, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        holder.txtNoticeTitle.setText(dataList.get(position).getTitle());
        holder.txtNoticeBrief.setText(dataList.get(position).getBrief());
        holder.txtNoticeFilePath.setText(dataList.get(position).getFileSource());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView txtNoticeTitle, txtNoticeBrief, txtNoticeFilePath;

        NoticeViewHolder(View itemView) {
            super(itemView);
            txtNoticeTitle =  itemView.findViewById(R.id.txt_notice_title);
            txtNoticeBrief =  itemView.findViewById(R.id.txt_notice_brief);
            txtNoticeFilePath =  itemView.findViewById(R.id.txt_notice_file_path);
        }
    }
}
```
8. Add uses-permission to Manifest.xml
```
public class MainActivity extends AppCompatActivity {

    private NoticeAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /** Create handle for the RetrofitInstance interface*/
        GetNoticeDataService service = RetrofitInstance.getRetrofitInstance().create(GetNoticeDataService.class);

        /** Call the method with parameter in the interface to get the notice data*/
        Call<NoticeList> call = service.getNoticeData();

        /**Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<NoticeList>() {
            @Override
            public void onResponse(Call<NoticeList> call, Response<NoticeList> response) {
                    generateNoticeList(response.body().getNoticeArrayList());
            }

            @Override
            public void onFailure(Call<NoticeList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Method to generate List of notice using RecyclerView with custom adapter*/
    private void generateNoticeList(ArrayList<Notice> noticeArrayList) {
        recyclerView = findViewById(R.id.recycler_view_notice_list);
        adapter = new NoticeAdapter(noticeArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
```

#### References
https://medium.com/cr8resume/make-your-hand-dirty-with-retrofit-2-a-type-safe-http-client-for-android-and-java-c546f88b3a51
