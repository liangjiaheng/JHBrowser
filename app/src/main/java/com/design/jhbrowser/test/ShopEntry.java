package com.design.jhbrowser.test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AdamL on 2017/5/21.
 */

public class ShopEntry implements Serializable {
    /**
     * begin_at : 1460390400000
     * city_id : 140
     * data : {"list":[{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038821","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":3570,"name":"小明同学台湾料理（将台路店）升级便当套餐","offline_time":1503071940000,"original_price":6400,"postage_free":false,"price":5500,"product_id":1038821,"product_image":"https://image.ricebook.com/business/20618399023243","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494950400000,"short_description":"多款便当全新升级","short_name":"小明同学台湾料理（将台路店）升级便当套餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081693]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038647","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4993,"merchant_id":2543,"name":"馥泰阁海鲜餐吧猪肚鸡双人餐","offline_time":1503071940000,"original_price":35800,"postage_free":false,"price":19800,"product_id":1038647,"product_image":"https://image.ricebook.com/business/20617350023062","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494950400000,"short_description":"还原地道广式猪肚鸡做法","short_name":"馥泰阁海鲜餐吧猪肚鸡双人餐","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081373]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038812","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4983,"merchant_id":6607,"name":"小明同学（通盈中心店）升级便当套餐","offline_time":1503071940000,"original_price":6400,"postage_free":false,"price":5500,"product_id":1038812,"product_image":"https://image.ricebook.com/business/20618397523235","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494950400000,"short_description":"多款便当全新升级","short_name":"小明同学（通盈中心店）升级便当套餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081677]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038700","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":5479,"name":"金茂威斯汀大饭店交流大堂吧日式下午茶","offline_time":1503071940000,"original_price":26800,"postage_free":false,"price":19800,"product_id":1038700,"product_image":"https://image.ricebook.com/business/20617930323199","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494950400000,"short_description":"创意十足的日式和风下午茶","short_name":"金茂威斯汀大饭店交流大堂吧日式下午茶","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081454]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038718","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4998,"merchant_id":10438,"name":"北京粤财 JW 万豪酒店 JW Kitchen 海鲜午餐自助","offline_time":1503158340000,"original_price":38800,"postage_free":false,"price":22800,"product_id":1038718,"product_image":"https://image.ricebook.com/business/20628355023693","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1495065600000,"short_description":"含 ENJOY 独享红酒礼遇","short_name":"北京粤财 JW 万豪酒店 JW Kitchen 海鲜午餐自助","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5081492]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038459","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4992,"merchant_id":10422,"name":"韩香馆（交道口店）招牌双人餐","offline_time":1503158340000,"original_price":28700,"postage_free":false,"price":14300,"product_id":1038459,"product_image":"https://image.ricebook.com/business/20625908123143","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1495036800000,"short_description":"绝无味精的地道韩国家常味","short_name":"韩香馆（交道口店）招牌双人餐","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081044]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038394","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":10419,"name":"藏红花西餐厅下午茶套餐","offline_time":1503071940000,"original_price":29900,"postage_free":false,"price":26600,"product_id":1038394,"product_image":"https://image.ricebook.com/business/20617533423112","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494950400000,"short_description":"五道营胡同里的浪漫下午茶","short_name":"藏红花西餐厅下午茶套餐","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080939]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038534","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":5541,"name":"Mono Cheese（王府井点）单人餐","offline_time":1502985540000,"original_price":5700,"postage_free":false,"price":4600,"product_id":1038534,"product_image":"https://image.ricebook.com/business/20610519023039","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494892800000,"short_description":"贝果、三明治、帕尼尼与芝士的诱人结合","short_name":"Mono Cheese（王府井点）单人餐","show_entity_name":"位 ","storage_state":1,"sub_product_id_list":[5081179,5081177,5081178]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038715","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4996,"merchant_id":5497,"name":"北京万豪酒店城墙小馆晚餐自助","offline_time":1511107140000,"original_price":29800,"postage_free":false,"price":16800,"product_id":1038715,"product_image":"https://image.ricebook.com/business/20625494523085","product_type":0,"refund_type":2,"sell_remain_time_state":1,"sell_state":1,"shelving_time":1495036800000,"short_description":"多国美味汇聚狂欢盛宴","short_name":"北京万豪酒店城墙小馆晚餐自助","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081482,5081479]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038644","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":10440,"name":"Ferdinand Grill & Bar 费迪牛排&酒吧招牌单人套餐","offline_time":1502985540000,"original_price":6800,"postage_free":false,"price":6000,"product_id":1038644,"product_image":"https://image.ricebook.com/business/20609370423386","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494864000000,"short_description":"ENJOY 用户更有专享优惠","short_name":"Ferdinand Grill & Bar 费迪牛排&酒吧招牌单人套餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081364,5081367,5081365,5081366]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038638","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4998,"merchant_id":10418,"name":"Laker's （丽都店）招牌单人餐","offline_time":1502985540000,"original_price":7300,"postage_free":false,"price":4600,"product_id":1038638,"product_image":"https://image.ricebook.com/business/20609081023358","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494864000000,"short_description":"人气小馆超值之选","short_name":"Laker's （丽都店）招牌单人餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081358,5081359]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038447","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4988,"merchant_id":10401,"name":"星洲老爷工作日套餐","offline_time":1502899140000,"original_price":5500,"postage_free":false,"price":5500,"product_id":1038447,"product_image":"https://image.ricebook.com/business/20600788623003","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494777600000,"short_description":"新加坡总理御厨送上地道南洋味","short_name":"星洲老爷工作日套餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081036,5081038,5081033,5081034]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038971","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4993,"merchant_id":3135,"name":"北京北辰洲际酒店 Lobby Lounge 大堂吧樱桃下午茶自助","offline_time":1498838340000,"original_price":15800,"postage_free":false,"price":14000,"product_id":1038971,"product_image":"https://image.ricebook.com/business/20635394923198","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1495123200000,"short_description":"夏日专享的甜蜜献礼","short_name":"北京北辰洲际酒店 Lobby Lounge 大堂吧樱桃下午茶自助","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5082112]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038579","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4998,"merchant_id":10293,"name":"京城63号院·四合有雲精选单人餐","offline_time":1502985540000,"original_price":28200,"postage_free":false,"price":19800,"product_id":1038579,"product_image":"https://image.ricebook.com/business/20609950423024","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494892800000,"short_description":"八宝坑胡同里的私房云南菜","short_name":"京城63号院·四合有雲精选单人餐","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5081241,5081242]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038969","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4998,"merchant_id":3983,"name":"Oyster Talks 蚝吧环球生蚝拼盘","offline_time":1500566340000,"original_price":36800,"postage_free":false,"price":17800,"product_id":1038969,"product_image":"https://image.ricebook.com/business/20635543423178","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1495152000000,"short_description":"6款全球精选生蚝打造饕客级体验","short_name":"Oyster Talks 蚝吧环球生蚝拼盘","show_entity_name":"份","storage_state":1,"sub_product_id_list":[5082105,5082106]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038264","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4994,"merchant_id":10337,"name":"川石和鳗鳗鱼套餐","offline_time":1502899140000,"original_price":47800,"postage_free":false,"price":39400,"product_id":1038264,"product_image":"https://image.ricebook.com/business/20600804523020","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494806400000,"short_description":"藏身老使馆区的鳗鱼专门店","short_name":"川石和鳗鳗鱼套餐","show_entity_name":"2位","storage_state":1,"sub_product_id_list":[5080611]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038468","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4985,"merchant_id":10402,"name":" JUSTIN 文秀单人餐","offline_time":1502899140000,"original_price":42700,"postage_free":false,"price":38800,"product_id":1038468,"product_image":"https://image.ricebook.com/business/20600986123050","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494777600000,"short_description":"新加坡总理御厨送上精致法餐","short_name":" JUSTIN 文秀单人餐","show_entity_name":"位","storage_state":1,"stunt":"厨师精选","sub_product_id_list":[5081588,5081054]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038641","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":10418,"name":"Laker's（丽都店）披萨分享餐","offline_time":1503158340000,"original_price":24700,"postage_free":false,"price":16800,"product_id":1038641,"product_image":"https://image.ricebook.com/business/20626117523182","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1495036800000,"short_description":"多人分享欢乐加倍","short_name":"Laker's（丽都店）披萨分享餐","show_entity_name":"2-3位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081360]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038968","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4998,"merchant_id":10451,"name":"JOJO'S BISTRO 烤鸡套餐","offline_time":1503244740000,"original_price":28100,"postage_free":false,"price":21800,"product_id":1038968,"product_image":"https://image.ricebook.com/business/20635678723221","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1495152000000,"short_description":"可供2-3人分享的香草烤鸡","short_name":"JOJO'S BISTRO 烤鸡套餐","show_entity_name":"2-3位","storage_state":1,"sub_product_id_list":[5082103]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038512","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":2911,"name":"将将 TIENSTIENS 工作日午餐","offline_time":1503071940000,"original_price":8600,"postage_free":false,"price":6000,"product_id":1038512,"product_image":"https://image.ricebook.com/business/20617667223139","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494950400000,"short_description":"经典法式简餐与甜品美酒","short_name":"将将 TIENSTIENS 工作日午餐","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5081133]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038517","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4997,"merchant_id":10394,"name":"Genius At Work（蓝色港湾店）单人套餐","offline_time":1502899140000,"original_price":14400,"postage_free":false,"price":9800,"product_id":1038517,"product_image":"https://image.ricebook.com/business/20600664323010","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494777600000,"short_description":"丰盛餐品一人独享","short_name":"Genius At Work（蓝色港湾店）单人套餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081138]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038474","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4998,"merchant_id":8508,"name":"小云南（东直门店）双人套餐","offline_time":1502899140000,"original_price":24600,"postage_free":false,"price":18800,"product_id":1038474,"product_image":"https://image.ricebook.com/business/20599399023016","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494777600000,"short_description":"老胡同里的淳朴滇味","short_name":"小云南（东直门店）双人套餐","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081065]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038435","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":5000,"merchant_id":1121,"name":"国贸大酒店粽子礼盒","offline_time":1496159940000,"original_price":26800,"postage_free":false,"price":24100,"product_id":1038435,"product_image":"https://image.ricebook.com/business/20599255423003","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494777600000,"short_description":"6款精美典雅的礼盒随心选","short_name":"国贸大酒店粽子礼盒","show_entity_name":"份","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080983,5080984,5080985,5080987,5080989,5080993]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038521","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4994,"merchant_id":6607,"name":"小明同学（通盈中心店）台湾北部粽子礼盒","offline_time":1497628740000,"original_price":13200,"postage_free":false,"price":11800,"product_id":1038521,"product_image":"https://image.ricebook.com/business/20600929723024","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494777600000,"short_description":"不一样的节日风味","short_name":"小明同学（通盈中心店）台湾北部粽子礼盒","show_entity_name":"份","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5081148,5081149]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038524","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4981,"merchant_id":10131,"name":"福楼毕斯罗 F·Bistronome 午餐","offline_time":1501257540000,"original_price":37600,"postage_free":false,"price":22800,"product_id":1038524,"product_image":"https://image.ricebook.com/business/20445162023184","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494777600000,"short_description":"含 ENJOY 独享礼遇","short_name":"福楼毕斯罗 F·Bistronome 午餐","show_entity_name":"2位","storage_state":1,"sub_product_id_list":[5081203]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038387","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4995,"merchant_id":1290,"name":"康莱德酒店陆羽中餐厅双人餐","offline_time":1502812740000,"original_price":60200,"postage_free":false,"price":23800,"product_id":1038387,"product_image":"https://image.ricebook.com/business/20591958523493","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494691200000,"short_description":"4折即享的超值双人工作餐","short_name":"康莱德酒店陆羽中餐厅双人餐","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080932]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1037908","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4980,"merchant_id":1586,"name":"王府半岛酒店 JING 餐厅单人午餐","offline_time":1499011140000,"original_price":21850,"postage_free":false,"price":19000,"product_id":1037908,"product_image":"https://image.ricebook.com/business/20539938623913","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494691200000,"short_description":"精选三道式星级套餐","short_name":"王府半岛酒店 JING 餐厅单人午餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5079811]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038372","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":5000,"merchant_id":10292,"name":"喜来登长城饭店茶园 Atrium下午茶套餐","offline_time":1514735940000,"original_price":16800,"postage_free":false,"price":12800,"product_id":1038372,"product_image":"https://image.ricebook.com/business/20584995823293","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494604800000,"short_description":"悦享静谧午后时光","short_name":"喜来登长城饭店茶园 Atrium下午茶套餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080906,5080907]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038361","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4996,"merchant_id":10337,"name":"川石和鳗寿司套餐","offline_time":1502726340000,"original_price":35800,"postage_free":false,"price":25300,"product_id":1038361,"product_image":"https://image.ricebook.com/business/20584224723286","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494604800000,"short_description":"藏身老使馆区的专业日料店","short_name":"川石和鳗寿司套餐","show_entity_name":"2位","storage_state":1,"sub_product_id_list":[5080878]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038371","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4995,"merchant_id":7478,"name":"京怀石 壬生卯月御献立套餐","offline_time":1502726340000,"original_price":80000,"postage_free":false,"price":68000,"product_id":1038371,"product_image":"https://image.ricebook.com/business/19993357723136","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494604800000,"short_description":"珍馐食材打造的十道式时令飨宴","short_name":"京怀石 壬生卯月御献立套餐","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5080904]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038369","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4995,"merchant_id":10394,"name":"Genius At Work （蓝色港湾店）招牌菜套餐","offline_time":1502726340000,"original_price":23400,"postage_free":false,"price":16500,"product_id":1038369,"product_image":"https://image.ricebook.com/business/20583589923192","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494604800000,"short_description":"一餐品味地道英伦风","short_name":"Genius At Work （蓝色港湾店）招牌菜套餐","show_entity_name":"2-3位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080898,5080899,5080900,5080901]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038340","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4995,"merchant_id":5541,"name":"Mono Cheese（王府井店）单人下午茶","offline_time":1502639940000,"original_price":6800,"postage_free":false,"price":5100,"product_id":1038340,"product_image":"https://image.ricebook.com/business/20575154523164","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494547200000,"short_description":"芝士蛋糕与饮品打造\u201c超浓郁\u201d午后","short_name":"Mono Cheese（王府井店）单人下午茶","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5080832]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038314","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4995,"merchant_id":4938,"name":"希尔顿逸林酒店逸轩西餐厅单人餐","offline_time":1497369540000,"original_price":45000,"postage_free":false,"price":19800,"product_id":1038314,"product_image":"https://image.ricebook.com/business/20574316023075","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494518400000,"short_description":" 依据时令推出的鲜美之味","short_name":"希尔顿逸林酒店逸轩西餐厅单人餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080744]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038291","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":5000,"merchant_id":3926,"name":"The VEGGIE TABLE招牌素食餐","offline_time":1502639940000,"original_price":10300,"postage_free":false,"price":9200,"product_id":1038291,"product_image":"https://image.ricebook.com/business/17172123813597","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494518400000,"short_description":"全天可享的轻宜素食","short_name":"The VEGGIE TABLE招牌素食餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080666,5080674]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038202","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4993,"merchant_id":10328,"name":"吉食行乐多人餐","offline_time":1502553540000,"original_price":21700,"postage_free":false,"price":18900,"product_id":1038202,"product_image":"https://image.ricebook.com/business/20566458423468","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494432000000,"short_description":"刺少肉细嫩的江团鱼升级经典菜品","short_name":"吉食行乐多人餐","show_entity_name":"2-3位","storage_state":1,"sub_product_id_list":[5080477]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038201","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4991,"merchant_id":10394,"name":"Genius At Work（蓝色港湾店）单人早午餐","offline_time":1502553540000,"original_price":14600,"postage_free":false,"price":9800,"product_id":1038201,"product_image":"https://image.ricebook.com/business/20566762323492","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494432000000,"short_description":"英式早午餐的优雅享受","short_name":"Genius At Work（蓝色港湾店）单人早午餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080476]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038101","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4999,"merchant_id":10134,"name":"于是 By Cartelei 双人下午茶套餐","offline_time":1502553540000,"original_price":19400,"postage_free":false,"price":11800,"product_id":1038101,"product_image":"https://image.ricebook.com/business/20556734323049","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494432000000,"short_description":"品质优雅的高端下午茶","short_name":"于是 By Cartelei 双人下午茶套餐","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080262,5080273,5080263]},{"area":"大望路","distance":0,"enjoy_url":"enjoyapp://product/detail?id=1013268","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4957,"merchant_id":1233,"name":"北京 JW 万豪酒店亚洲风尚餐厅单人午餐自助","offline_time":1503935940000,"original_price":33600,"postage_free":false,"price":18800,"product_id":1013268,"product_image":"https://image.ricebook.com/business/18984797513035","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1468252800000,"short_description":"午餐时段的超值盛宴","short_name":"北京 JW 万豪酒店亚洲风尚餐厅单人午餐自助","show_entity_name":"位","storage_state":1,"stunt":"自助精选","sub_product_id_list":[5068102,5080663,5062792,5025137,5040813]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1037525","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4974,"merchant_id":1233,"name":"北京 JW 万豪酒店亚洲风尚餐厅晚餐自助","offline_time":1498838340000,"original_price":45770,"postage_free":false,"price":22800,"product_id":1037525,"product_image":"https://image.ricebook.com/business/16865853023023","product_type":0,"refund_type":2,"sell_remain_time_state":1,"sell_state":1,"shelving_time":1493654400000,"short_description":"五星畅食限时赏味","short_name":"北京 JW 万豪酒店亚洲风尚餐厅晚餐自助","show_entity_name":"位","storage_state":1,"stunt":"星级酒店","sub_product_id_list":[5081602,5080662,5079031]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038209","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4987,"merchant_id":1586,"name":"王府半岛酒店大堂茶座双人下午茶","offline_time":1496851140000,"original_price":45770,"postage_free":false,"price":39800,"product_id":1038209,"product_image":"https://image.ricebook.com/business/20566079623429","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494432000000,"short_description":"体验传统英式下午茶文化","short_name":"王府半岛酒店大堂茶座双人下午茶","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080485]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038206","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4993,"merchant_id":1942,"name":"中国大饭店阿丽雅西餐厅周末早午餐","offline_time":1501516740000,"original_price":34300,"postage_free":false,"price":29600,"product_id":1038206,"product_image":"https://image.ricebook.com/business/20565356323326","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494432000000,"short_description":"乐享惬意悠闲的周末时光","short_name":"中国大饭店阿丽雅西餐厅周末早午餐","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080482]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038199","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":12,"merchant_id":10351,"name":"北京王府井希尔顿酒店  Chef Chandra 主厨晚宴预售","offline_time":1495900740000,"original_price":67620,"postage_free":false,"price":58800,"product_id":1038199,"product_image":"https://image.ricebook.com/business/20566298723449","product_type":0,"refund_type":1,"sell_state":1,"shelving_time":1494460800000,"short_description":"每日限量12席发售 ","short_name":"北京王府井希尔顿酒店  Chef Chandra 主厨晚宴预售","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5080465,5080467,5080469,5080471,5080466,5080468,5080470,5080472]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038011","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4992,"merchant_id":7478,"name":"京怀石 壬生小皿怀石套餐","offline_time":1502553540000,"original_price":35000,"postage_free":false,"price":26800,"product_id":1038011,"product_image":"https://image.ricebook.com/business/20566651923492","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494460800000,"short_description":"小皿怀石套餐","short_name":"京怀石 壬生小皿怀石套餐","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5080078]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038207","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4955,"merchant_id":1858,"name":"炭匠炉端烧特选活鳗鱼双人套餐","offline_time":1502553540000,"original_price":58800,"postage_free":false,"price":24600,"product_id":1038207,"product_image":"https://image.ricebook.com/business/20565850923369","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494432000000,"short_description":"特选新鲜活鳗现场制作","short_name":"炭匠炉端烧特选活鳗鱼双人套餐","show_entity_name":"2位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080483]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038200","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4975,"merchant_id":9259,"name":"隐厨新双人餐","offline_time":1502553540000,"original_price":22300,"postage_free":false,"price":20000,"product_id":1038200,"product_image":"https://image.ricebook.com/business/20566643423463","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494432000000,"short_description":"含两款招牌菜单可选","short_name":"隐厨新双人餐","show_entity_name":"2位","storage_state":1,"sub_product_id_list":[5080473,5080474]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038134","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4998,"merchant_id":1834,"name":"庆云楼 nuage Brunch","offline_time":1504195140000,"original_price":33900,"postage_free":false,"price":22800,"product_id":1038134,"product_image":"https://image.ricebook.com/business/15548220113788","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494460800000,"short_description":"畅享丰富越南早午餐","short_name":"庆云楼 nuage Brunch","show_entity_name":"2位","storage_state":1,"stunt":"厨师精选","sub_product_id_list":[5080357]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038098","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4993,"merchant_id":10337,"name":"川石和鳗鳗鱼饭商务午餐","offline_time":1502467140000,"original_price":14000,"postage_free":false,"price":8500,"product_id":1038098,"product_image":"https://image.ricebook.com/business/20563168623208","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494374400000,"short_description":"藏身老使馆区的鳗鱼专门店","short_name":"川石和鳗鳗鱼饭商务午餐","show_entity_name":"位","storage_state":1,"sub_product_id_list":[5080257]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038054","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4989,"merchant_id":1046,"name":"Feast Food By EAST 单人周末早午餐自助","offline_time":1504195140000,"original_price":30800,"postage_free":false,"price":29800,"product_id":1038054,"product_image":"https://image.ricebook.com/business/18795098413944","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494345600000,"short_description":"美好周末从丰富美肴开始","short_name":"Feast Food By EAST 单人周末早午餐自助","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080235]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1038060","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4876,"merchant_id":7452,"name":"新加坡海鲜新品点心自助","offline_time":1498752000000,"original_price":10800,"postage_free":false,"price":9800,"product_id":1038060,"product_image":"https://image.ricebook.com/business/19287833123341","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494345600000,"short_description":"品味地道南洋风情","short_name":"新加坡海鲜新品点心自助","show_entity_name":"位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5080259]},{"distance":0,"enjoy_url":"enjoyapp://product/detail?id=1037700","entity_name":"份","favorite_num":0,"is_favorite":false,"is_new":false,"left_count":4992,"merchant_id":6476,"name":"M 小买鱼生套餐","offline_time":1502467140000,"original_price":39000,"postage_free":false,"price":22800,"product_id":1037700,"product_image":"https://image.ricebook.com/business/20557019823083","product_type":0,"refund_type":2,"sell_state":1,"shelving_time":1494345600000,"short_description":"全天可享的超值日料","short_name":"M 小买鱼生套餐","show_entity_name":"2-4位","storage_state":1,"stunt":"超值精选","sub_product_id_list":[5079362]}]}
     * end_at : 1577808000000
     * now : 1495378266953
     * position : 0
     * style : 8
     */

    private long begin_at;
    private int city_id;
    private DataBean data;
    private long end_at;
    private long now;
    private int position;
    private int style;

    public long getBegin_at() {
        return begin_at;
    }

    public void setBegin_at(long begin_at) {
        this.begin_at = begin_at;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public long getEnd_at() {
        return end_at;
    }

    public void setEnd_at(long end_at) {
        this.end_at = end_at;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * distance : 0
             * enjoy_url : enjoyapp://product/detail?id=1038821
             * entity_name : 份
             * favorite_num : 0
             * is_favorite : false
             * is_new : false
             * left_count : 4999
             * merchant_id : 3570
             * name : 小明同学台湾料理（将台路店）升级便当套餐
             * offline_time : 1503071940000
             * original_price : 6400
             * postage_free : false
             * price : 5500
             * product_id : 1038821
             * product_image : https://image.ricebook.com/business/20618399023243
             * product_type : 0
             * refund_type : 2
             * sell_state : 1
             * shelving_time : 1494950400000
             * short_description : 多款便当全新升级
             * short_name : 小明同学台湾料理（将台路店）升级便当套餐
             * show_entity_name : 位
             * storage_state : 1
             * stunt : 超值精选
             * sub_product_id_list : [5081693]
             * sell_remain_time_state : 1
             * area : 大望路
             */

            private int distance;
            private String enjoy_url;
            private String entity_name;
            private int favorite_num;
            private boolean is_favorite;
            private boolean is_new;
            private int left_count;
            private int merchant_id;
            private String name;
            private long offline_time;
            private int original_price;
            private boolean postage_free;
            private int price;
            private int product_id;
            private String product_image;
            private int product_type;
            private int refund_type;
            private int sell_state;
            private long shelving_time;
            private String short_description;
            private String short_name;
            private String show_entity_name;
            private int storage_state;
            private String stunt;
            private int sell_remain_time_state;
            private String area;
            private List<Integer> sub_product_id_list;

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public String getEnjoy_url() {
                return enjoy_url;
            }

            public void setEnjoy_url(String enjoy_url) {
                this.enjoy_url = enjoy_url;
            }

            public String getEntity_name() {
                return entity_name;
            }

            public void setEntity_name(String entity_name) {
                this.entity_name = entity_name;
            }

            public int getFavorite_num() {
                return favorite_num;
            }

            public void setFavorite_num(int favorite_num) {
                this.favorite_num = favorite_num;
            }

            public boolean isIs_favorite() {
                return is_favorite;
            }

            public void setIs_favorite(boolean is_favorite) {
                this.is_favorite = is_favorite;
            }

            public boolean isIs_new() {
                return is_new;
            }

            public void setIs_new(boolean is_new) {
                this.is_new = is_new;
            }

            public int getLeft_count() {
                return left_count;
            }

            public void setLeft_count(int left_count) {
                this.left_count = left_count;
            }

            public int getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(int merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getOffline_time() {
                return offline_time;
            }

            public void setOffline_time(long offline_time) {
                this.offline_time = offline_time;
            }

            public int getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(int original_price) {
                this.original_price = original_price;
            }

            public boolean isPostage_free() {
                return postage_free;
            }

            public void setPostage_free(boolean postage_free) {
                this.postage_free = postage_free;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getProduct_image() {
                return product_image;
            }

            public void setProduct_image(String product_image) {
                this.product_image = product_image;
            }

            public int getProduct_type() {
                return product_type;
            }

            public void setProduct_type(int product_type) {
                this.product_type = product_type;
            }

            public int getRefund_type() {
                return refund_type;
            }

            public void setRefund_type(int refund_type) {
                this.refund_type = refund_type;
            }

            public int getSell_state() {
                return sell_state;
            }

            public void setSell_state(int sell_state) {
                this.sell_state = sell_state;
            }

            public long getShelving_time() {
                return shelving_time;
            }

            public void setShelving_time(long shelving_time) {
                this.shelving_time = shelving_time;
            }

            public String getShort_description() {
                return short_description;
            }

            public void setShort_description(String short_description) {
                this.short_description = short_description;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getShow_entity_name() {
                return show_entity_name;
            }

            public void setShow_entity_name(String show_entity_name) {
                this.show_entity_name = show_entity_name;
            }

            public int getStorage_state() {
                return storage_state;
            }

            public void setStorage_state(int storage_state) {
                this.storage_state = storage_state;
            }

            public String getStunt() {
                return stunt;
            }

            public void setStunt(String stunt) {
                this.stunt = stunt;
            }

            public int getSell_remain_time_state() {
                return sell_remain_time_state;
            }

            public void setSell_remain_time_state(int sell_remain_time_state) {
                this.sell_remain_time_state = sell_remain_time_state;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public List<Integer> getSub_product_id_list() {
                return sub_product_id_list;
            }

            public void setSub_product_id_list(List<Integer> sub_product_id_list) {
                this.sub_product_id_list = sub_product_id_list;
            }
        }
    }
}
