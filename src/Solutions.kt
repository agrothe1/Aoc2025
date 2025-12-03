import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.add
import org.jetbrains.kotlinx.dataframe.api.forEach
import org.jetbrains.kotlinx.dataframe.api.take
import org.jetbrains.kotlinx.dataframe.io.read
import java.io.File
import kotlin.math.abs

fun day1(pInput: List<String>): Int{
    fun List<String>.accLR()=fold(mutableListOf(50)){acc, s->
        acc.add(acc.last()+s.let{(if(it.startsWith('L'))-1 else 1)*it.drop(1).toInt()}); acc}
    val acc=pInput.accLR()
    val r=acc.count{it%100==0}
    val r2=acc.windowed(2,1).map{abs(it.first() - it.last())}
    //pInput.forEach{println(it)}
    return 0
}

fun day2(pInput: List<String>){
    val rgex=Regex("""(.+)\1+""")
    val x=pInput.flatMap{it.split(",")}
        .map{it.split("-")}
        .map{(it.first().toLong()..it.last().toLong())}
        .flatMap{it.toList()}
        .map{Pair(it, rgex.matches(it.toString()))}
        .filter{it.second}.sumOf{it.first}
    println(x)
}

@Suppress("SimplifiableCallChain")
fun day3(pInput: List<String>) =
    pInput
        .map{l->l.map{it.digitToInt()}}
        .map{l->
            l.flatMapIndexed{idx, frst->
                l.drop(idx+1).map{scnd->Pair(frst, scnd)}
            }.distinct().maxOf{it.first*10+it.second}
        }.sum()

fun main(){
    //val r=day1(File("input/Day1_exp.txt").readLines())
    //val r=day2(File("input/Day2.txt").readLines())
    val r=day3(File("input/Day3.txt").readLines())
    println(r)
}

fun mainx(){
    DataFrame.read("input/movies/movies.csv", ',',
        header=listOf("movieId", "title", "genres"), skipLines=1)
    .let{df->
        val dfWithYear=df.add("year"){
            val titleText = "title"<String>()
            "\\d{4}".toRegex()
                .findAll(titleText)
                .lastOrNull()?.value
        }
        dfWithYear.take(100).forEach{println(it)}
    }
}