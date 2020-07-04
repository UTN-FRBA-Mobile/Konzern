package utn.frba.mobile.konzern.news

import utn.frba.mobile.konzern.posts.repository.BasePostRepository
import utn.frba.mobile.konzern.posts.repository.NewsRepository
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel

class NewsViewModel: BasePostViewModel() {
    override val repository: BasePostRepository = NewsRepository()
    override val canEdit: Boolean
        get() = false
}