package com.hshim.lottomanager.enums.lotto

enum class NumberBuildAlgorithm(
    val title: String,
    val description: String,
    val disable: Boolean = false,
) {
    SIMPLE_RANDOM(
        title = "단순 난수",
        description = "중복되지 않는 6개의 숫자를 생성합니다.",
    ),
    METAHEURISTIC(
        title = "메타 휴리스틱 알고리즘",
        description = "역대 이력들을 최적화하여 비교적 빠른 최적의 번호를 생성합니다.",
    ),
    GENETIC(
        title = "유전 알고리즘",
        description = "역대 이력들에 점수를 부여하여 유전적 규칙을 찾아 다음 세대 번호를 예측 생성합니다.",
    ),
    APRIORI(
        title = "Apriori 알고리즘",
        description = "역대 이력들 중 함께 자주 등장하는 조합을 모아 연관 규칙을 찾아내어 번호를 생성합니다.",
        disable = true,
    )
}