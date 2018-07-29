package com.blog.marublo;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class AbstractPointController implements PointInterface{

	protected WebDriver driver;

	public AbstractPointController(){

		FirefoxOptions options = new FirefoxOptions();
		//options.addPreference(key, value)
		options.addPreference("browser.chrome.image_icons.max_size", 0);
		options.addPreference("browser.display.show_image_placeholders", false);
		options.addPreference("browser.download.manager.addToRecentDocs", false);
		options.addPreference("browser.formfill.expire_days", 30);
		options.addPreference("browser.link.open_newwindow.restriction", 0);
		options.addPreference("browser.overlink-delay", 0);
		options.addPreference("browser.search.openintab", true);
		options.addPreference("browser.sessionhistory.max_entries", 7);
		options.addPreference("browser.sessionhistory.max_total_viewers", 1);
		options.addPreference("browser.sessionstore.interval", 300000);
		options.addPreference("browser.sessionstore.max_windows_undo", 1);
		options.addPreference("browser.tabs.animate", false);
		options.addPreference("browser.tabs.closeWindowWithLastTab", false);
		options.addPreference("browser.tabs.insertRelatedAfterCurrent", false);
		options.addPreference("browser.tabs.loadBookmarksInBackground", true);
		options.addPreference("browser.tabs.warnOnCloseOtherTabs", false);
		options.addPreference("browser.taskbar.lists.enabled", false);
		options.addPreference("browser.taskbar.lists.frequent.enabled", false);
		options.addPreference("browser.taskbar.lists.tasks.enabled", false);
		options.addPreference("browser.taskbar.previews.max", 1);
		options.addPreference("browser.urlbar.filter.javascript", false);
		options.addPreference("browser.urlbar.formatting.enabled", false);
		options.addPreference("browser.urlbar.trimURLs", false);
		options.addPreference("dom.ipc.plugins.flash.subprocess.crashreporter.enabled", false);
		options.addPreference("dom.popup_maximum", 1);
		options.addPreference("extensions.getAddons.cache.enabled", false);
		options.addPreference("extensions.pocket.enabled", false);
		options.addPreference("general.smoothScroll.mouseWheel.durationMaxMS", 150);
		options.addPreference("general.smoothScroll.mouseWheel.durationMinMS", 150);
		options.addPreference("geo.enabled", false);
		options.addPreference("gfx.direct2d.force-enabled", true);
		options.addPreference("gfx.font_rendering.cleartype.always_use_for_content", true);
		options.addPreference("keyword.enabled", false);
		options.addPreference("layers.acceleration.force-enabled", true);
		options.addPreference("layout.css.report_errors", false);
		options.addPreference("layout.word_select.eat_space_to_next_word", false);
		options.addPreference("media.autoplay.enabled", false);
		options.addPreference("mousewheel.default.delta_multiplier_y", 150);
		options.addPreference("network.dnsCacheExpiration", 86400);
		options.addPreference("network.http.request.max-start-delay", 0);
		options.addPreference("network.http.spdy.enabled.http2", false);
		options.addPreference("network.http.speculative-parallel-limit", 0);
		options.addPreference("privacy.trackingprotection.enabled", true);
		options.addPreference("view_source.wrap_long_lines", true);

		options.addPreference("browser.cache.memory.enable", true);
		options.addPreference("browser.cache.disk.enable", false);


		options.addPreference("network.http.pipelining", true);
		options.addPreference("network.http.pipelining.ssl", true);

    	//options.setProfile(profile);


		driver = new FirefoxDriver(options);
	}

	@Override
	public void execute() throws InterruptedException, IOException {


	}

	public void quitDriver() {
		driver.quit();
	}





}
