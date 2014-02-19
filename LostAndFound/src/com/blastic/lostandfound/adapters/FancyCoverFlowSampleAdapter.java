/*
 * Copyright 2013 David Schreiber
 * 2013 John Paul Nalog
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blastic.lostandfound.adapters;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {

	// =============================================================================
	// Private members
	// =============================================================================

	private ArrayList<Bitmap> fancyPics = new ArrayList<Bitmap>();

	// =============================================================================
	// Supertype overrides
	// =============================================================================

	public FancyCoverFlowSampleAdapter(ArrayList<Bitmap> b) {
		fancyPics = b;
	}

	@Override
	public int getCount() {
		return fancyPics.size();
	}

	@Override
	public Bitmap getItem(int i) {
		return fancyPics.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
		ImageView imageView = null;

		if (reuseableView != null) {
			imageView = (ImageView) reuseableView;
		} else {
			Bitmap image = this.getItem(i);			
			imageView = new ImageView(viewGroup.getContext());
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			if(image.getWidth() > image.getHeight())
				imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(320, 220));
			else
				imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(220, 320));

		}

		imageView.setImageBitmap(this.getItem(i));
		return imageView;
	}

}