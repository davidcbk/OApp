package com.cubikosolutions.dampgl.ejemplopcpartes.parte;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.cubikosolutions.dampgl.ejemplopcpartes.R;
import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.G;
import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.Utilidades;
import com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.Contrato;
import com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.ParteProveedor;

import java.io.FileNotFoundException;

public class ParteListFragment extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {
	


	ParteCursorAdapter mAdapter;
	LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	ActionMode mActionMode;
	View viewSeleccionado;

	public static ParteListFragment newInstance() {
		ParteListFragment f = new ParteListFragment();

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		if(G.VERSION_ADMINISTRADOR) { //solo aparece el + si es admin
			MenuItem menuItem = menu.add(Menu.NONE, G.INSERTAR, Menu.NONE, "Insertar");
			menuItem.setIcon(R.drawable.ic_nuevo_registro1);
			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case G.INSERTAR:
				Intent intent = new Intent(getActivity(),ParteInsertarActivity.class);
				startActivity(intent);
					break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * The Fragment's UI is just a simple text view showing its
	 * instance number.
	 */



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//Log.i(LOGTAG, "onCreateView");
		View v = inflater.inflate(R.layout.fragment_parte_list, container, false);

		mAdapter = new ParteCursorAdapter(getActivity());
		setListAdapter(mAdapter);

		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Log.i(LOGTAG, "onActivityCreated");

		mCallbacks = this;

		getLoaderManager().initLoader(0, null, mCallbacks);

		if(G.VERSION_ADMINISTRADOR) {  //solo aparece el menu de poder editar si es admin

			getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
					if (mActionMode != null) {
						return false;
					}
					mActionMode = getActivity().startActionMode(mActionModeCallback);
					view.setSelected(true);
					viewSeleccionado = view;
					return true;
				}
			});

		}

	}

	ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){

		@Override
		public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
			MenuInflater inflater = actionMode.getMenuInflater();
			inflater.inflate(R.menu.menu_contextual, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
			switch (menuItem.getItemId()){
				case R.id.menu_borrar:
					int parteId =(Integer) viewSeleccionado.getTag();
					ParteProveedor.deleteRecordConBitacora(getActivity().getContentResolver(),parteId);

					break;
				case R.id.menu_editar:
                    Intent intent = new Intent(getActivity(), ParteModificarActivity.class);
					parteId =(Integer) viewSeleccionado.getTag();
                    intent.putExtra(Contrato.Parte._ID,parteId);
                    startActivity(intent);
					break;
			}
			mActionMode.finish();
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode actionMode) {
			mActionMode = null;
		}
	};

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created.  This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.


		//MUESTRA LOS DATOS EN EL LISTADO
		String columns[] = new String[] { Contrato.Parte._ID,
				Contrato.Parte.MOTIVO,
				Contrato.Parte.CLIENTE,
				Contrato.Parte.FECHA,
				Contrato.Parte.RESOLUCION
										};

		Uri baseUri = Contrato.Parte.CONTENT_URI;

		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.

		String selection = null;

		return new CursorLoader(getActivity(), baseUri,
				columns, selection, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
		// old cursor once we return.)

		Uri laUriBase = Uri.parse("content://"+Contrato.AUTHORITY+"/Parte");
		data.setNotificationUri(getActivity().getContentResolver(), laUriBase);
		
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	public class ParteCursorAdapter extends CursorAdapter {
		public ParteCursorAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			int ID = cursor.getInt(cursor.getColumnIndex(Contrato.Parte._ID));

			String motivo = cursor.getString(cursor.getColumnIndex(Contrato.Parte.MOTIVO));
			String cliente = cursor.getString(cursor.getColumnIndex(Contrato.Parte.CLIENTE));
			String fecha = cursor.getString(cursor.getColumnIndex(Contrato.Parte.FECHA));
			String resolucion = cursor.getString(cursor.getColumnIndex(Contrato.Parte.RESOLUCION));
	


			TextView textviewMotivo = (TextView) view.findViewById(R.id.textview_parte_list_item_motivo);
			textviewMotivo.setText(motivo);
			TextView textviewCliente = (TextView) view.findViewById(R.id.textview_parte_list_item_cliente);
			textviewCliente.setText(cliente);
			TextView textviewFecha = (TextView) view.findViewById(R.id.textview_parte_list_item_fecha);
			textviewFecha.setText(fecha);
			TextView textviewResolucion = (TextView) view.findViewById(R.id.textview_parte_list_item_resolucion);
			textviewResolucion.setText(resolucion);

			ImageView image = (ImageView) view.findViewById(R.id.image_view);

			try {
				Utilidades.loadImageFromStorage(getActivity(), "img_" + ID + ".jpg", image); // si exitse imagen lo generra como avatar
			} catch (FileNotFoundException e) {
				ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
				int color = generator.getColor(cliente); //Genera un color según el nombre como icono del cliente
				TextDrawable drawable = TextDrawable.builder()
						.buildRound(cliente.substring(0,1), color);
				image.setImageDrawable(drawable);
			}

			view.setTag(ID);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.parte_list_item, parent, false);
			bindView(v, context, cursor);
			return v;
		}
	}
}
