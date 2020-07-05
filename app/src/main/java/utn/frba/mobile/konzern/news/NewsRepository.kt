package utn.frba.mobile.konzern.news

import utn.frba.mobile.konzern.posts.repository.BasePostRepository

class NewsRepository: BasePostRepository() {
    override var dbCollectionName = "news"
}