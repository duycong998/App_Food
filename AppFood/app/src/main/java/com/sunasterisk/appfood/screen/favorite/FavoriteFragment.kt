package com.sunasterisk.appfood.screen.favorite

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.*
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.db.RecipeDao
import com.sunasterisk.appfood.data.db.RecipeDatabase
import com.sunasterisk.appfood.data.model.AddPicture
import com.sunasterisk.appfood.data.model.Recipe
import com.sunasterisk.appfood.screen.detail.DetailFragment
import com.sunasterisk.appfood.screen.home.HomeFragment
import com.sunasterisk.food_01.utils.OnItemRecyclerViewClickListenner
import com.sunasterisk.food_01.utils.SendDataFragment
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.item_add_recipe.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.concurrent.Executors
import kotlin.random.Random


@Suppress("DEPRECATION")
class FavoriteFragment : Fragment(), OnItemRecyclerViewClickListenner<Recipe>,
    SendDataFragment<Recipe> {
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }
    private var listRecipe = mutableListOf<Recipe>()
    private val REQUEST_CODE_CAMERA = 123
    private val REQUEST_CODE_FOLDER = 456
    private lateinit var uri: String
    private lateinit var recipe: Recipe
    private var listAdd = mutableListOf<AddPicture>()
    private var diaLog: Dialog? = null
    // private var diaLogEdit: Dialog? = null
    private var recipeDatabase: RecipeDatabase? = null
    private var recipeDao: RecipeDao? = null
    //private lateinit var list: List<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempFile: File =
            File.createTempFile("camera", ".png", requireActivity().externalCacheDir)
        uri = tempFile.absolutePath

        adapter.registerItemRecyclerViewClickListener(this)
        adapter.registerItemLongLickRecyclerViewClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbarFavorite)
        toolbarFavorite.run { toolbarFavorite.title = "" }
        setHasOptionsMenu(true)
        init()
    }

    private fun init() {
        Executors.newSingleThreadExecutor().execute {
            recipeDatabase = RecipeDatabase.getDatabaseRecipe(requireContext())
            recipeDao = recipeDatabase?.recipeDao()
            val data = recipeDao!!.getDataAll()
            if (data.isEmpty()) {
                Handler(Looper.getMainLooper()).post {
                    recyclerViewFavorite.visibility = View.GONE
                    textViewTitle.visibility = View.VISIBLE
                }
            } else {
                Handler(Looper.getMainLooper()).post {
                    recyclerViewFavorite.visibility = View.VISIBLE
                    textViewTitle.visibility = View.GONE
                    adapter.updateData(data.toMutableList())
                    recyclerViewFavorite.adapter = adapter
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_cv, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuAdd) {
            addRecipe()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addRecipe() {
        val screenSize = Point()
        diaLog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        requireActivity().windowManager.defaultDisplay.getSize(screenSize)
        diaLog!!.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.item_add_recipe)
            window?.apply {
                setLayout(screenSize.x * 14 / 15, screenSize.y * 14 / 15)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.gravity = Gravity.CENTER
            }
        }
        val popup = PopupWindow(requireContext())
        val listView = ListView(requireContext())
        listAdd()
        listView.adapter = AddPictureAdapter(requireContext(), listAdd)

        listView.setOnItemClickListener { _, _: View, i, _ ->
            if (i == 0) {
                openCamera()
                Toast.makeText(requireContext(), "cam", Toast.LENGTH_SHORT).show()
            } else {
                openFolder()
                Toast.makeText(requireContext(), "folder", Toast.LENGTH_SHORT).show()
            }
            popup.dismiss()
        }
        popup.width = 400
        popup.height = 300
        popup.contentView = listView

        diaLog!!.linearAddPicture.setOnClickListener {
            popup.showAsDropDown(it)
        }
        diaLog!!.buttonConfrim.setOnClickListener {
            if (checkData()) {
                Toast.makeText(requireContext(), "Vui Long Nhap Day Du", Toast.LENGTH_SHORT).show()
            } else {
                val bitmapDrawable = diaLog!!.edtvideoPlayYoutube.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val byteArray = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
                val img = byteArray.toByteArray()

                val id = Random.nextInt(10000, 99999).toByte().toString()
                val name = diaLog!!.edtNameDetail.text.toString().trim()
                val area = diaLog!!.edtAreaDetail.text.toString().trim()
                val urlvideo = diaLog!!.textAddVideo.text.toString().trim()
                val edtInstructionDetail = diaLog!!.edtInstructionDetail.text.toString().trim()
                val edtIngredientDetail = diaLog!!.edtIngredientDetail.text.toString().trim()
                val edtIngredientDetail2 = diaLog!!.edtIngredientDetail2.text.toString().trim()
                val edtIngredientDetail3 = diaLog!!.edtIngredientDetail3.text.toString().trim()
                val edtIngredientDetail4 = diaLog!!.edtIngredientDetail4.text.toString().trim()
                val edtIngredientDetail5 = diaLog!!.edtIngredientDetail5.text.toString().trim()
                val edtMeasure1 = diaLog!!.edtMeasure1.text.toString().trim()
                val edtMeasure2 = diaLog!!.edtMeasure2.text.toString().trim()
                val edtMeasure3 = diaLog!!.edtMeasure3.text.toString().trim()
                val edtMeasure4 = diaLog!!.edtMeasure4.text.toString().trim()
                val edtMeasure5 = diaLog!!.edtMeasure5.text.toString().trim()
                recipe = Recipe(
                    idRecipe = id,
                    name = name,
                    area = area,
                    bitmap = img,
                    urlVideo = urlvideo,
                    instructions = edtInstructionDetail,
                    ingre1 = edtIngredientDetail,
                    ingre2 = edtIngredientDetail2,
                    ingre3 = edtIngredientDetail3,
                    ingre4 = edtIngredientDetail4,
                    ingre5 = edtIngredientDetail5,
                    measure1 = edtMeasure1,
                    measure2 = edtMeasure2,
                    measure3 = edtMeasure3,
                    measure4 = edtMeasure4,
                    measure5 = edtMeasure5
                )
//                listRecipe.add(
//                    Recipe(
//                        idRecipe = id,
//                        name = name,
//                        area = area,
//                        bitmap = img,
//                        urlVideo = urlvideo,
//                        instructions = edtInstructionDetail,
//                        ingre1 = edtIngredientDetail,
//                        ingre2 = edtIngredientDetail2,
//                        ingre3 = edtIngredientDetail3,
//                        ingre4 = edtIngredientDetail4,
//                        ingre5 = edtIngredientDetail5,
//                        measure1 = edtMeasure1,
//                        measure2 = edtMeasure2,
//                        measure3 = edtMeasure3,
//                        measure4 = edtMeasure4,
//                        measure5 = edtMeasure5
//                    )
                //            )
                Executors.newSingleThreadExecutor().execute {
                    recipeDao!!.insert(recipe)
                    listRecipe.add(recipe)
                    init()
                    // val list = recipeDao!!.getDataAll().toMutableList()
                }

                //  adapter.updateData(listRecipe)
                Toast.makeText(requireContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show()
                diaLog!!.dismiss()
            }
        }
        diaLog!!.buttonHuy.setOnClickListener {
            diaLog!!.cancel()
        }
        diaLog!!.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data // lấy đường dẫn
            try {
                val inputStream: InputStream? = requireActivity().contentResolver.openInputStream(
                    uri!!
                ) // mở nơi đọc dữ liệu truyền vào uri
                val bitmapp =
                    BitmapFactory.decodeStream(inputStream) // lấy hình từ đường dẫn sau khi đọc
                val bitmapResize = Bitmap.createScaledBitmap(bitmapp, 640, 480, false)
                diaLog!!.edtvideoPlayYoutube.setImageBitmap(bitmapResize)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
            val bitmap = data.extras!!["data"] as Bitmap?
            val bitmapResize = bitmap?.let { Bitmap.createScaledBitmap(it, 640, 480, false) }
            diaLog!!.edtvideoPlayYoutube.setImageBitmap(bitmapResize)
        }
    }

    private fun checkData(): Boolean {
        return diaLog!!.run {
            edtNameDetail.text.isEmpty() ||
                    edtAreaDetail.text.isEmpty() ||
                    textAddVideo.text.isEmpty() ||
                    edtInstructionDetail.text.isEmpty() ||
                    edtIngredientDetail.text.isEmpty() ||
                    edtIngredientDetail2.text.isEmpty() ||
                    edtIngredientDetail3.text.isEmpty() ||
                    edtIngredientDetail4.text.isEmpty() ||
                    edtIngredientDetail5.text.isEmpty() ||
                    edtMeasure1.text.isEmpty() ||
                    edtMeasure2.text.isEmpty() ||
                    edtMeasure3.text.isEmpty() ||
                    edtMeasure4.text.isEmpty() ||
                    edtMeasure5.text.isEmpty() ||
                    edtvideoPlayYoutube.drawable == null
        }
    }

    private fun listAdd() {
        listAdd.add(AddPicture("Camera", R.drawable.ic_camera))
        listAdd.add(AddPicture("Folder", R.drawable.ic_folder))
    }

    private fun openCamera() {
        //check premisson
//        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA).apply {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(intent, REQUEST_CODE_CAMERA)
//        }

        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED.apply {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_CAMERA)
        }
    }

    private fun openFolder() {
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED.apply {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.setType("image/*")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(uri)))
            this@FavoriteFragment.startActivityForResult(intent, REQUEST_CODE_FOLDER)
        }
    }

    //check permission
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            REQUEST_CODE_CAMERA -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(intent, REQUEST_CODE_CAMERA)
//            } else {
//                Toast.makeText(requireContext(), "Bạn không cho phép mở cam", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            REQUEST_CODE_FOLDER -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                val intent =
//                    Intent(Intent.ACTION_PICK)
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST_CODE_FOLDER)
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "Bạn không cho phép mở thư mục ảnh",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

    override fun onItemClickListener(item: Recipe?) {
        HomeFragment.checkFavorite = true
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, DetailFragment.newInstance(item!!), "fragA")
        transaction.commit()
    }

    override fun onItemClick(item: Recipe?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Bạn có muốn xóa không???")
        builder.setPositiveButton("Có") { dialog, _ ->
            Executors.newSingleThreadExecutor().execute {
                recipeDao?.deleteRecipe(item!!)
            }
            adapter.deleteRecipe(item!!)
            dialog.dismiss()
        }

        builder.setNegativeButton("Không") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

//    private fun editRecipe() {
//        val diaLogEdit = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
//        diaLogEdit!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        diaLogEdit!!.setContentView(R.layout.item_edit_recipe)
//        val popup = PopupWindow(requireContext())
//        val listView = ListView(requireContext())
//        listAdd()
//        listView.adapter = AddPictureAdapter(requireContext(), listAdd)
//
//        listView.setOnItemClickListener { _, _: View, i, _ ->
//            if (i == 0) {
//                openCamera()
//                Toast.makeText(requireContext(), "cam", Toast.LENGTH_SHORT).show()
//            } else {
//                openFolder()
//                Toast.makeText(requireContext(), "folder", Toast.LENGTH_SHORT).show()
//            }
//            popup.dismiss()
//        }
//        popup.width = 400
//        popup.height = 300
//        popup.contentView = listView
//
//        diaLogEdit!!.linearAddPicture.setOnClickListener {
//            popup.showAsDropDown(it)
//        }
//        diaLogEdit.show()
//    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}
