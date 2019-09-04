package sti.com.livefeedback.ui.main.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import de.hdodenhof.circleimageview.CircleImageView;
import org.w3c.dom.Text;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.StoreResponse;
import sti.com.livefeedback.ui.main.stores.StoreFragment;
import sti.com.livefeedback.ui.storedetails.StoreDetailsActivity;
import sti.com.livefeedback.utils.BindingUtils;
import sti.com.livefeedback.utils.mapmarker.FloatingMarkerTitlesOverlay;
import sti.com.livefeedback.utils.mapmarker.MarkerInfo;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback , StoreFragment.MapFragmentCallback {

    private MapViewModel mViewModel;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    FloatingMarkerTitlesOverlay floatingMarkersOverlay;
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this); //this is important

        floatingMarkersOverlay = v.findViewById(R.id.map_floating_markers_overlay);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng latLng=new LatLng(18.5204, 73.8567);

        enableMyLocationIfPermitted();
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        //mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("pune"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    10);
        } else if (mGoogleMap != null) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    private void addMaker(LiveData<List<StoreResponse.Blog>> storeResponse) {
        for(int i=0;i<storeResponse.getValue().size();i++) {
            Double latDouble = Double.valueOf(String.valueOf(storeResponse.getValue().get(i).getCoordinates().get(1)));
            Double longDouble = Double.valueOf(String.valueOf(storeResponse.getValue().get(i).getCoordinates().get(0)));
            LatLng latLng = new LatLng(latDouble, longDouble);
            Bitmap bitmap=createIconForMarker(storeResponse.getValue().get(i));
            View view=LayoutInflater.from(getContext()).inflate(R.layout.map_maker_icon,null);
            bitmap=createBitmapFromLayout(view);


            Marker marker = mGoogleMap.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .snippet("Distance: "+storeResponse.getValue().get(i).getDistance().intValue()+" km")
                            .title(storeResponse.getValue().get(i).getName())
                            .visible(true)
                            //.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
                            //.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(getContext(),R.drawable.ic_account,"jayesh",storeResponse.getValue().get(i))))

            );
            marker.showInfoWindow();
            marker.setDraggable(true);
            marker.setTag(storeResponse.getValue().get(i).getStoreId());
            mGoogleMap.setOnMarkerClickListener(marker1 -> {
                Intent intent = StoreDetailsActivity.newIntent(getContext());
                intent.putExtra("storeId", marker1.getTag().toString());
                getContext().startActivity(intent);
                return false;
            });
            marker.showInfoWindow();
            //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));


            floatingMarkersOverlay.setSource(mGoogleMap);
            int color = Color.BLACK;
            MarkerInfo mi = new MarkerInfo(latLng, storeResponse.getValue().get(i).getName() + " "+storeResponse.getValue().get(i).getDistance().intValue()+" km", color);
            mGoogleMap.addMarker(new MarkerOptions().position(mi.getCoordinates()));
            floatingMarkersOverlay.addMarker(0, mi);
            //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            floatingMarkersOverlay.setTag(storeResponse.getValue().get(i).getStoreId());

        }

    }

    private Bitmap createIconForMarker(StoreResponse.Blog storeResponse) {

        ViewGroup view= (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.map_maker_icon,null);
        ImageView img=view.findViewById(R.id.img);
        TextView tvName=view.findViewById(R.id.tvName);
        tvName.setText(storeResponse.getName()+" - "+storeResponse.getDistance().intValue() +" km");
        BindingUtils.setImageUrl(img,storeResponse.getLogo());
        return createBitmapFromLayout(view);
    }
    private Bitmap createBitmapFromLayout(View tv) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tv.measure(spec, spec);
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(tv.getMeasuredWidth(), tv.getMeasuredWidth(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate((-tv.getScrollX()), (-tv.getScrollY()));
        tv.draw(c);
        return b;
    }


    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name, StoreResponse.Blog storeResponse) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView txt_name = (TextView)marker.findViewById(R.id.name);
        txt_name.setText(storeResponse.getName());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    @Override
    public void passStoreDetailsToMap(LiveData<List<StoreResponse.Blog>> storeData) {
        addMaker(storeData);
    }


    public void updateText(LiveData<List<StoreResponse.Blog>> storeData){
        addMaker(storeData);
    }
}
