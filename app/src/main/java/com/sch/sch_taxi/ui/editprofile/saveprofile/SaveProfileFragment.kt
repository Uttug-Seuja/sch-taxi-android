package com.sch.sch_taxi.ui.editprofile.saveprofile

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentSaveProfileBinding
import com.sch.sch_taxi.ui.setprofile.bottom.EditProfileImageBottomSheet
import com.sch.sch_taxi.util.customOnFocusChangeListener
import com.sch.sch_taxi.util.hideKeyboard
import com.sch.sch_taxi.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SaveProfileFragment : BaseFragment<FragmentSaveProfileBinding, SaveProfileViewModel>(R.layout.fragment_save_profile) {

    private val TAG = "SaveProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_save_profile

    override val viewModel : SaveProfileViewModel by viewModels()
    private val navController by lazy { findNavController() }

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private var cameraUri: Uri? = null

    // 요청하고자 하는 권한들
    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    // 권한을 허용하도록 요청
    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) toastMessage("권한 허용 필요")
        }
    }

    override fun initStartView() {
        binding.apply {
            this.viewmodel  = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRegisterForActivityResult()
        initEditText()
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is SaveProfileNavigationAction.NavigateToSuccess -> navController.popBackStack()
                    is SaveProfileNavigationAction.NavigateToEditProfileImg -> editProfileImageBottomSheet()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.profileName.collectLatest {
                if(it != "" && it != viewModel.beforeProfile!!.name) {
                    viewModel.editPossibleState.emit(true)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = "프로필"
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun editProfileImageBottomSheet() {
        requestMultiplePermission.launch(permissionList)
        val dialog = EditProfileImageBottomSheet {
            if(it) getGalleryImage()
            else getCaptureImage()

            lifecycleScope.launch {
                viewModel.editPossibleState.emit(true)
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun initRegisterForActivityResult() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult.data?.let {
                createFile(it.data!!)
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if(it) { cameraUri?.let { uri ->
                createFile(uri)
            } }
        }
    }

    private fun getGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        viewModel.isGalleryImage.value = true
        galleryLauncher.launch(intent)

    }

    private fun getCaptureImage() {
        viewModel.isGalleryImage.value = false
        cameraUri = createImageFile()
        cameraLauncher.launch(cameraUri)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        binding.userNameContents.customOnFocusChangeListener(requireContext())
        binding.profileEditMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.userNameContents.clearFocus()
            false
        }
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    private fun createFile(uri: Uri) {
        val file = uriToFile(uri, requireContext())
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        // Update Profile API
        viewModel.setFileToUri(file = requestBody)
    }
}
