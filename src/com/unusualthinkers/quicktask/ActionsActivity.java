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
		data = new Actions(R.drawable.actions_ec, "�����ȭ", "������ ��Ȳ�� ������ ��ó�ϼ���.");
		Array_Data.add(data);
		data = new Actions(R.drawable.actions_t, "������", "��ο� ���� ��� ���߼���.");
		Array_Data.add(data);
		data = new Actions(R.drawable.actions_call, "��ȭ", "������ ��ȭ������ �غ�����.");
		Array_Data.add(data);
		data = new Actions(R.drawable.actions_silence, "�������", "��Ƽ���� ���Ѻ�����.");
		Array_Data.add(data);
		adapter = new ActionsAdapter(getActivity(), R.layout.layout_text,
				Array_Data);
		li.setAdapter(adapter);
		li.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("InflateParams") @Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					final CharSequence[] items = { "������ (112)", "�ҹ漭 (119)",
							"��ø�Ű� (113)", "�м��Ű� (125)", "�������Ű� (127)",
							"���������� (111)", "�ؾ������ (122)", "�б����� (117)" };
					builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("�����ȭ");
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
					builder.setTitle("������");
					final CharSequence[] items = { "�ѱ�", "����" };
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
					builder.setTitle("��ȭ");
					builder.setPositiveButton("Ȯ��",
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
												"��ȭ��ȣ�� �Է����ּ���.",
												Toast.LENGTH_SHORT).show();
									}
								}
							});
					AlertDialog alert = builder.create();
					alert.show();
				} else if (position == 3) {
					aManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
					if (aManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
						Toast.makeText(getActivity(), "������尡 �̹� Ȱ��ȭ �Ǿ��ֽ��ϴ�.",
								Toast.LENGTH_SHORT).show();
					} else {
						aManager = (AudioManager) getActivity()
								.getSystemService(Context.AUDIO_SERVICE);
						aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(getActivity(), "������尡 Ȱ��ȭ �Ǿ����ϴ�.",
								Toast.LENGTH_SHORT).show();
					}
					
				}

			}
		});
		return v;
	}

}