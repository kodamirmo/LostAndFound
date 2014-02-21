package com.blastic.lostandfound.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class ImageUtils {
	
	public static void saveBitmapAsAPicture(Context context,Bitmap bitmap){
		try {
			
			String pictureName = System.currentTimeMillis() + ".jpg";
			
			File photoFile= new File(getPawhubFolder(), pictureName);
			OutputStream stream = new FileOutputStream(photoFile);
			
			bitmap.compress(CompressFormat.JPEG, 100, stream);
			stream.flush();
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_LONG).show();
		}
	}
	
	public static Bitmap imageFromGallery(Context context, Intent data, int reqWidth,int reqHeight) throws IOException {
		
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String profile_Path = cursor.getString(columnIndex);
		cursor.close();

		// First decode with inJustDecodeBounds=true to check dimensions

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap photo = BitmapFactory.decodeFile(profile_Path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		ExifInterface ei = new ExifInterface(profile_Path);
		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);

		photo = BitmapFactory.decodeFile(profile_Path, options);

		switch (orientation) {
		case ExifInterface.ORIENTATION_ROTATE_90:
			photo=rotateImage(photo, 90);
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			photo=rotateImage(photo, 180);
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			photo=rotateImage(photo, 270);
			break;
		}
		
		return photo;

	}

	public static Bitmap rotateImage(Bitmap setphoto2, int i) {
		Matrix matrix = new Matrix();
		matrix.setRotate(i);
		return Bitmap.createBitmap(setphoto2, 0, 0,setphoto2.getWidth(), setphoto2.getHeight(), matrix, false);
	}
	
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public static File getPawhubFolder(){
		File temp = new File(Environment.getExternalStorageDirectory(), File.separator + "Pawhub");
		if (!temp.exists())
			temp.mkdirs();
		return temp;
	}
}
