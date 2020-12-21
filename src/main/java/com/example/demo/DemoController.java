package com.example.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

  @Autowired
  BbsRepository repos;
  @Autowired
  DivisionRepository d_repos;

  /* 一覧画面（初期画面）への遷移 */
  @GetMapping
  public ModelAndView list() {
    ModelAndView mav = new ModelAndView();
    List<BulletinBoard> list = repos.findAll();
    mav.setViewName("bbs/list");
    mav.addObject("data", list);
    return mav;
  }

  /* 新規画面への遷移 */
  @GetMapping("/add")
  ModelAndView add() {
    ModelAndView mav = new ModelAndView();
    BulletinBoard data = new BulletinBoard();
    mav.addObject("bbs", data);
    mav.setViewName("bbs/new");
    // 分類テーブルの読み込み
    List<Division> list = d_repos.findAll();
    mav.addObject("lists", list);
    return mav;
  }

  /* 編集画面への遷移 */
  @GetMapping("/edit")
  ModelAndView edit(@RequestParam int id) {
    ModelAndView mav = new ModelAndView();
    BulletinBoard data = repos.findById(id);
    mav.addObject("bbs", data);
    mav.setViewName("bbs/new");
    // 分類テーブルの読み込み
    List<Division> list = d_repos.findAll();
    mav.addObject("lists", list);
    return mav;
  }

  @GetMapping("/show")
  ModelAndView show(@RequestParam int id) {
    ModelAndView mav = new ModelAndView();
    BulletinBoard data = repos.findById(id);
    mav.addObject("bbs", data);
    mav.setViewName("bbs/show");
    // 分類テーブルの読み込み
    Division div = d_repos.findById(data.getDivision());
    mav.addObject("div", div);
    return mav;
  }

  /* 更新処理 */
  @PostMapping("/create")
  @Transactional(readOnly = false)
  public ModelAndView save(@ModelAttribute("bbs") @Validated BulletinBoard bbs, BindingResult result) {
    if (result.hasErrors()) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("bbs/new");
      // 分類テーブルの読み込み
      List<Division> list = d_repos.findAll();
      mav.addObject("lists", list);
      mav.addObject("bbs", bbs);
      return mav;
    }
    // public ModelAndView save(@ModelAttribute("formModel") BulletinBoard bbs) {
    if (bbs.getId() == 0) {
      bbs.setCreatedDate(new Date());
    } else {
      bbs.setCreatedDate(repos.findById(bbs.getId()).getCreatedDate());
    }
    repos.saveAndFlush(bbs);
    return new ModelAndView("redirect:");
  }

  /* 削除処理 */
  @PostMapping("/delete")
  @Transactional(readOnly = false)
  public ModelAndView delete(@RequestParam int id) {
    repos.deleteById(id);
    return new ModelAndView("redirect:");
  }

  /* 初期データ作成 */
  @PostConstruct
  public void init() {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MONTH, -2);
    // 掲示板初期データの登録
    BulletinBoard bbs1 = new BulletinBoard();
    bbs1.setCreatedDate(c.getTime());
    bbs1.setTitle("帰社日について");
    bbs1.setCreateUser("松江　太郎");
    bbs1.setContent("帰社日は以下の通りです。2018/01/11 2018/02/13");
    bbs1.setDivision(1);
    repos.saveAndFlush(bbs1);

    bbs1 = new BulletinBoard();
    bbs1.setCreatedDate(c.getTime());
    bbs1.setTitle("新入社員歓迎会のお知らせ");
    bbs1.setCreateUser("松江　太郎");
    bbs1.setContent("新入社員歓迎会を実施します");
    bbs1.setDivision(1);
    repos.saveAndFlush(bbs1);

    // 分類テーブル初期データの登録
    Division div1 = new Division();
    div1.setId(1);
    div1.setName("通達/連絡");
    d_repos.saveAndFlush(div1);

    div1 = new Division();
    div1.setId(2);
    div1.setName("会議開催について");
    d_repos.saveAndFlush(div1);

    div1 = new Division();
    div1.setId(3);
    div1.setName("スケジュール");
    d_repos.saveAndFlush(div1);

    div1 = new Division();
    div1.setId(4);
    div1.setName("イベント");
    d_repos.saveAndFlush(div1);

    div1 = new Division();
    div1.setId(5);
    div1.setName("その他");
    d_repos.saveAndFlush(div1);
  }
}
