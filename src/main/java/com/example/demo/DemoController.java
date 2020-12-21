package com.example.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

  @Autowired
  BbsRepository repos;

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
    mav.addObject("formModel", data);
    mav.setViewName("bbs/new");
    return mav;
  }

  /* 編集画面への遷移 */
  @GetMapping("/edit")
  ModelAndView edit(@RequestParam int id) {
    ModelAndView mav = new ModelAndView();
    BulletinBoard data = repos.findById(id);
    mav.addObject("formModel", data);
    mav.setViewName("bbs/new");
    return mav;
  }

  @GetMapping("/show")
  ModelAndView show(@RequestParam int id) {
    ModelAndView mav = new ModelAndView();
    BulletinBoard data = repos.findById(id);
    mav.addObject("formModel", data);
    mav.setViewName("bbs/show");
    return mav;
  }

  /* 更新処理 */
  @PostMapping("/create")
  @Transactional(readOnly = false)
  public ModelAndView save(@ModelAttribute("formModel") BulletinBoard bbs) {
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
    BulletinBoard bbs1 = new BulletinBoard();
    bbs1.setContent("島根県松江市浜乃木1-2-3");
    bbs1.setTitle("TITLE");
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MONTH, -2);
    bbs1.setCreatedDate(c.getTime());
    bbs1.setCreateUser("島根　花子");

    repos.saveAndFlush(bbs1);

  }
}
