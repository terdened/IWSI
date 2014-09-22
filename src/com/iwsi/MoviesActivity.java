package com.iwsi;

import java.util.LinkedList;
import java.util.Locale;

import com.iwsi.downloadmanager.DownloadFragment;
import com.iwsi.model.MovieModel;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesActivity extends ActionBarActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	static LinkedList<MovieModel> mMovieModel;
	static int[] mMoviesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies);
		

		mMovieModel = new LinkedList<MovieModel>();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(this,
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mMoviesList= getIntent().getIntArrayExtra("moviesList");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movies, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private int mCount;
		private final Activity mActivity;
		
		public SectionsPagerAdapter(final Activity pActivity, FragmentManager fm) {
			super(fm);
			mActivity=pActivity;
			mCount = 1;
		}
		
		public void onFragmentLoaded(String pJSON)
		{
			MovieModel movie = new MovieModel(mActivity, pJSON);
			mMovieModel.add(movie);
			if(mMovieModel.size()>1)
			{
				mCount++;
			}
			else
			{
				this.notifyDataSetChanged();
				this.getItem(0);
			}
			this.notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			
			if((position==0)&&(mMovieModel.size()==0))
			{
				String url=getResources().getString(R.string.host)+"movie.php?movieId="+mMoviesList[0];
				new DownloadFragment(this).execute(url);
			}
			else
			if((position==mMovieModel.size()-1)&&(position<mMoviesList.length-1))
			{
				String url=getResources().getString(R.string.host)+"movie.php?movieId="+mMoviesList[mMovieModel.size()];
				new DownloadFragment(this).execute(url);
			}
			
			return PlaceholderFragment.newInstance(position + 1);
		}
		
		@Override
		public int getItemPosition(Object object) {
		    return POSITION_NONE;
		}

		@Override
		public int getCount() {
			return mCount;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			default:
				return getString(R.string.title_section_default).toUpperCase(l);
			}
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		int mPosition;
		
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			
			PlaceholderFragment fragment = new PlaceholderFragment();
			
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			args.putInt("mPosition", sectionNumber-1);
			fragment.setArguments(args);
			
			return fragment;
		}

		public PlaceholderFragment() {
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			mPosition = getArguments() != null ? getArguments().getInt("mPosition") : 1;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_movie,
					container, false);
			
			if(mMovieModel.size()!=0)
			{
				TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
				movieTitle.setText(mMovieModel.get(mPosition).mTitle);
				
				TextView movieDescription = (TextView) rootView.findViewById(R.id.movie_description);
				movieDescription.setText(mMovieModel.get(mPosition).mDescription);
				
				ImageView moviePoster = (ImageView) rootView.findViewById(R.id.poster);
				moviePoster.setImageBitmap(mMovieModel.get(mPosition).mPoster);
				
				Gallery gallery = (Gallery) rootView.findViewById(R.id.gallery);
				
				gallery.setAdapter(new GalleryAdapter(this.getActivity(), mMovieModel.get(mPosition).mScreenshots));
			}
			
			return rootView;
		}
	}

}
