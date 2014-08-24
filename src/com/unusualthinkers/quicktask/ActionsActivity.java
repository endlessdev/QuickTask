package com.unusualthinkers.quicktask;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ActionsActivity extends Fragment {
	private ActionsAdapter adapter;
	private Actions data;
	private ArrayList<Actions> Array_Data;
	AlertDialog.Builder builder;
	private Camera ca;
	private Parameters p;
	Dialog dlg;
	AudioManager aManager;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_actions, container, false);
		Array_Data = new ArrayList<Actions>();
		final ListView li = (ListView) v.findViewById(R.id.al);
		data = new Actions(R.drawable.actions_ec, "긴급전화", "위급한 상황에 빠르게 대처하세요.");
		Array_Data.add(data);
		data = new Actions(R.drawable.actions_t, "손전등", "어두운 곳을 밝게 비추세요.");
		Array_Data.add(data);
		data = new Actions(R.drawable.actions_call, "전화", "빠르게 전화연결을 해보세요.");
		Array_Data.add(data);
		data = new Actions(R.drawable.actions_silence, "무음모드", "에티켓을 지켜보세요.");
		Array_Data.add(data);
		adapter = new ActionsAdapter(getActivity(), R.layout.layout_text,
				Array_Data);
		li.setAdapter(adapter);
		li.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("InflateParams") @Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					final CharSequence[] items = { "경찰서 (112)", "소방서 (119)",
							"간첩신고 (113)", "밀수신고 (125)", "마약사범신고 (127)",
							"국가정보원 (111)", "해양경찰서 (122)", "학교폭력 (117)" };
					builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("긴급전화");
					builder.setItems(items,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									if (which == 0) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 112));
										startActivity(i);
									} else if (which == 1) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 119));
										startActivity(i);
									} else if (which == 2) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 113));
										startActivity(i);
									} else if (which == 3) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 125));
										startActivity(i);
									} else if (which == 4) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 127));
										startActivity(i);
									} else if (which == 5) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 111));
										startActivity(i);
									} else if (which == 6) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 122));
										startActivity(i);
									} else if (which == 7) {
										Intent i = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + 117));
										startActivity(i);
									}

								}
							});
					AlertDialog alert = builder.create();
					alert.show();
				} else if (position == 1) {
					builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("손전등");
					final CharSequence[] items = { "켜기", "끄기" };
					int selected = -1;
					builder.setSingleChoiceItems(items, selected,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int position) {
									if (position == 0) {
										try {
											ca = Camera.open();
											p = ca.getParameters();
											p.setFlashMode(Parameters.FLASH_MODE_TORCH);
											ca.setParameters(p);
											ca.startPreview();
										} catch (Exception e) {
										}

									} else if (position == 1) {
										try {
											p.setFlashMode(Parameters.FLASH_MODE_OFF);
											ca.setParameters(p);
											ca.stopPreview();
											ca.release();
										} catch (Exception e) {
										}
									}
								}
							});
					AlertDialog alert = builder.create();
					alert.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							try {
								p.setFlashMode(Parameters.FLASH_MODE_OFF);
								ca.setParameters(p);
								ca.stopPreview();
								ca.release();
							} catch (Exception e) {
							}
						}
					});
					alert.show();
				} else if (position == 2) {
					builder = new AlertDialog.Builder(getActivity());
					LayoutInflater inflater = getActivity().getLayoutInflater();
					View layout = inflater
							.inflate(R.layout.layout_dialog, null);
					final EditText numberInput = (EditText) layout
							.findViewById(R.id.ac_e);
					final Editable str = numberInput.getText();
					builder.setView(layout);
					builder.setTitle("전화");
					builder.setPositiveButton("확인",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int position) {
									if (str.toString().length() > 0) {
										Intent i = new Intent(
												Intent.ACTION_CALL,
												Uri.parse("tel:"
														+ str.toString()));
										startActivity(i);

									} else {
										Toast.makeText(getActivity(),
												"전화번호를 입력해주세요.",
												Toast.LENGTH_SHORT).show();
									}
								}
							});
					AlertDialog alert = builder.create();
					alert.show();
				} else if (position == 3) {
					aManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
					if (aManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
						Toast.makeText(getActivity(), "무음모드가 이미 활성화 되어있습니다.",
								Toast.LENGTH_SHORT).show();
					} else {
						aManager = (AudioManager) getActivity()
								.getSystemService(Context.AUDIO_SERVICE);
						aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(getActivity(), "무음모드가 활성화 되었습니다.",
								Toast.LENGTH_SHORT).show();
					}
					
				}

			}
		});
		return v;
	}

}