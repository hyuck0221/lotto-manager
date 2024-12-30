package com.hshim.lottomanager.model.send

import com.hshim.lottomanager.database.lotto.Lotto
import com.hshim.lottomanager.database.lotto.LottoNumber

class LottoOpenMessage(
    title: String,
    html: String,
) : SendModel(title, html) {
    constructor(lotto: Lotto, lottoNumbers: List<LottoNumber>) : this(
        title = "${lotto.times}회 로또 결과",
        html = when {
            lottoNumbers.isNotEmpty() -> {
                var html = """
                  <h1>총 ${lottoNumbers.count { it.rank != 0 }}건의 당첨 이력이 있습니다</h1>
                  <p>${lotto.times}회 로또 당첨번호: ${lotto.numbers} + ${lotto.bonusNumber}</p>
                  <p>총 당첨금: ${lotto.totalPrize} | 1등 수령금: ${lotto.firstWinnerPrize}</p>
                  <p>총 1등 수: ${lotto.winCnt} | 1등 인당 수령금: ${(lotto.firstWinnerPrize ?: 0) / (lotto.winCnt ?: 1)}</p>
                  <h2>등록한 로또 내역</h2>
                  <table>
                    <thead>
                      <tr>
                        <th>로또번호</th>
                        <th>당첨 등수</th>
                      </tr>
                    </thead>
                    <tbody>
                    """.trimIndent()
                lottoNumbers
                    .sortedBy { if (it.rank == 0) 6 else it.rank }
                    .forEach { lottoNumber ->
                        html += """<tr>
                            <td>${lottoNumber.numbers}</td>
                            <td>${
                            when (lottoNumber.rank) {
                                0, null -> "낙첨"
                                else -> lottoNumber.rank.toString()
                            }
                        }</td>
                            </tr>
                            """.trimIndent()
                    }
                html += """
                </tbody>
              </table>
            """.trimIndent()
                html
            }

            else -> """
                <h1>총 ${lottoNumbers.count { it.rank != 0 }}건의 당첨 이력이 있습니다</h1>
                <p>${lotto.times}회 로또 당첨번호: ${lotto.numbers}</p>
                <p>총 당첨금: ${lotto.totalPrize} | 1등 수령금: ${lotto.firstWinnerPrize}</p>
                <p>총 1등 수: ${lotto.winCnt} | 1등 인당 수령금: ${(lotto.firstWinnerPrize ?: 0) / (lotto.winCnt ?: 1)}</p>
                <h2>등록한 로또 내역</h2>
                <p>로또 내역이 존재하지 않습니다.</p>
            """.trimIndent()
        }
    )
}