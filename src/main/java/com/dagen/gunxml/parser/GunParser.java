package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.Firearm;

import java.nio.file.Path;
import java.util.List;

public interface GunParser {

    List<Firearm> parse(Path file);
}
