package com.zrj.coolweather.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrj.coolweather.R;
import com.zrj.coolweather.db.City;
import com.zrj.coolweather.db.County;
import com.zrj.coolweather.db.Province;
import com.zrj.coolweather.util.HttpUtil;
import com.zrj.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by a on 2017/3/30.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    public static final String URL = "http://guolin.tech/api/china";
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button buttonBack;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province seletedProvince;
    private City seletedCity;
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container);
        titleText = (TextView) view.findViewById(R.id.title_text);
        buttonBack = (Button) view.findViewById(R.id.button_back);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvinces();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    seletedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    seletedCity = cityList.get(position);
                    queryCounties();
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                } else if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                }
            }
        });
    }

    private void queryProvinces() {
        titleText.setText("中国");
        buttonBack.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(URL, "province");
        }
    }

    private void queryCities() {
        titleText.setText(seletedProvince.getProvinceName());
        buttonBack.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId = ?", String.valueOf(seletedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            currentLevel = LEVEL_CITY;
        } else {
            String url = URL + "/" + seletedProvince.getProvinceCode();
            queryFromServer(url, "city");
        }
    }

    private void queryCounties() {
        titleText.setText(seletedCity.getCityName());
        buttonBack.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?", String.valueOf(seletedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            currentLevel = LEVEL_COUNTY;
        } else {
            String url = URL + "/" + seletedProvince.getProvinceCode() + "/" + seletedCity.getCityCode();
            queryFromServer(url, "county");
        }
    }

    private void queryFromServer(String url, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            boolean result = false;

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                if (type.equals("province")) {
                    result = Utility.handleProviceResponse(responseContent);
                } else if (type.equals("city")) {
                    result = Utility.handleCityResponse(responseContent, seletedProvince.getId());
                } else if (type.equals("county")) {
                    result = Utility.handleCountyResponse(responseContent, seletedCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (type.equals("province")) {
                                queryProvinces();
                            } else if (type.equals("city")) {
                                queryCities();
                            } else if (type.equals("county")) {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
















